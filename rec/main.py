from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from fastapi.responses import Response
from fastapi import FastAPI, HTTPException, UploadFile, File
import pandas as pd
from io import StringIO
import logging

from starlette.responses import JSONResponse

from BookUserData import BookUserData
from surprise import SVD
from Evaluator import Evaluator

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

from models.Model import Comment, Book, MemberRecommendBook

# 동기 엔진 및 세션 생성
SYNC_DB_URL = "mysql+pymysql://root:!A123456bc%40@j10d206.p.ssafy.io:3306/ssafy"
engine = create_engine(SYNC_DB_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

app = FastAPI()


@app.post("/upload-reviews/")
def upload_reviews(file: UploadFile = File(...)):  # 동기 함수
    db = SessionLocal()
    try:
        if file.content_type != 'text/csv':
            raise HTTPException(status_code=400, detail="File must be a CSV.")

        content = file.file.read()
        df = pd.read_csv(StringIO(content.decode('utf-8')))

        for index, row in df.iterrows():
            isbn = row['isbn']
            member_id = row['member_id']
            rating = row['rating']
            comment_text = row['comment']
            try:
                book_tuple = db.query(Book.book_id).filter(Book.isbn == isbn).first()
                if not book_tuple:
                    raise ValueError(f"ISBN {isbn}에 해당하는 책을 찾을 수 없습니다.")
                book_id = book_tuple[0]
                new_comment = Comment(member_id=member_id, book_id=book_id, rating=rating, comment=comment_text)
                db.add(new_comment)

            except Exception as inner_e:
                logger.exception(f"책 조회 중 에러 발생: {inner_e}")
                # 필요한 경우, 여기서 더 구체적인 예외 처리를 수행할 수 있습니다.

        # 커밋은 모든 작업이 성공적으로 완료된 후에 수행
        db.commit()
    except HTTPException as http_e:
        # HTTPException은 FastAPI가 클라이언트에게 적절한 HTTP 응답을 생성하도록 합니다.
        raise http_e
    except Exception as e:
        db.rollback()
        logger.exception("리뷰 업로드 중 예외 발생")
        raise HTTPException(status_code=500, detail="An error occurred while uploading reviews.")
    finally:
        db.close()

    return {"message": "Reviews successfully uploaded and processed."}


def LoadBookData():
    ml = BookUserData()
    print("Loading book ratings...")
    data = ml.loadBookLatestSmall()
    print("\nComputing book popularity ranks so we can measure novelty later...")
    rankings = ml.getPopularityRanks()
    return (ml, data, rankings)


def get_unique_userIds(csv_file_path):
    df = pd.read_csv(csv_file_path)
    unique_userIds = df['userId'].unique()  # 'userId' 컬럼에서 고유한 값들을 추출
    return unique_userIds


def fetch_data_and_save_to_csv(filename="reviews.csv"):
    db = SessionLocal()
    try:
        query = db.query(Comment.member_id, Comment.book_id, Comment.rating, Comment.created_at).all()

        df = pd.DataFrame(query, columns=['userId', 'bookId', 'rating', 'timestamp'])

        file_path = f"./{filename}"
        df.to_csv(file_path, index=False, header=['userId', 'bookId', 'rating', 'timestamp'])
        return file_path
    finally:
        db.close()


# Load up common data set for the recommender algorithms
(ml, evaluationData, rankings) = LoadBookData()

# Construct an Evaluator to, you know, evaluate them
evaluator = Evaluator(evaluationData, rankings)

# SVD
SVD = SVD()


@app.post("/recommend/")
def recommend():
    db = SessionLocal()  # 세션 시작
    ids = get_unique_userIds("reviews.csv")
    evaluator.AddAlgorithm(SVD, "SVD")

    try:
        for user_id in ids.tolist():
            n_recs = evaluator.SampleTopNRecs(ml, user_id=user_id)
            for rec in n_recs:
                new_rec = MemberRecommendBook(member_id=user_id, book_id=rec[0])
                print(new_rec)
                db.add(new_rec)
        db.commit()
    except Exception as e:
        db.rollback()  # 에러 발생 시 롤백
        print(e)  # 에러 로깅
        return JSONResponse(status_code=500, content={"message": "Insert failed"})
    finally:
        db.close()  # 세션 종료

    return JSONResponse(status_code=200, content={"message": "Insert successful"})



@app.get("/download-reviews/")
def download_reviews():
    csv_data = fetch_data_and_save_to_csv()
    return Response(content=csv_data, media_type="text/csv",
                    headers={"Content-Disposition": "attachment; filename=reviews.csv"})
