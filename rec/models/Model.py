from sqlalchemy import Column, Integer, String, ForeignKey, Date, BigInteger
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


class Book(Base):
    __tablename__ = 'book'

    book_id = Column(Integer, primary_key=True)
    isbn = Column(String)

    comments = relationship("Comment", back_populates="book")

class Comment(Base):
    __tablename__ = 'comment'

    comment_id = Column(Integer, primary_key=True)
    comment = Column(String)
    created_at = Column(Date)
    rating = Column(Integer)
    member_id = Column(Integer, ForeignKey('member.member_id'))
    book_id = Column(Integer, ForeignKey('book.book_id'))

    member = relationship("Member", back_populates="comments")
    book = relationship("Book", back_populates="comments")


class Member(Base):
    __tablename__ = 'member'

    member_id = Column(Integer, primary_key=True)
    # 다른 멤버 필드 정의

    comments = relationship("Comment", back_populates="member")
    # 다른 관계 정의

class MemberRecommendBook(Base):
    __tablename__ = 'member_recommend_book'
    member_recommend_id = Column(Integer, primary_key=True)
    member_id = Column(BigInteger, ForeignKey('member.member_id'))
    book_id = Column(Integer, ForeignKey('book.book_id'))
    created_at = Column(Date, default=func.now())
