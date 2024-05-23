import deleteButton from "../../assets/profile/delete.png";

const NotRead = ({ books, handleClickBook, handleUpdateBookshelf, handleDeleteBookshelf }) => {
  return (
    <div className="grid lg:grid-cols-4 sm:grid-cols-2">
      {books.map((book) => (
        <div key={book.bookId} className="text-center relative group">
          <div className="absolute right-4 top-4 cursor-pointer opacity-0 group-hover:opacity-100" 
               onClick={() => handleDeleteBookshelf(book.bookshelfId)}>
            <img className="w-4 h-4" src={deleteButton} alt="삭제버튼" />
          </div>
          <div className="m-3 cursor-pointer" onClick={() => handleClickBook(book.bookId)}>
            <img src={book.cover} alt={book.title} className="flex w-[310px] h-[440px] items-center justify-center" />
            <p className="m-1 font-bold">{book.title}</p>
          </div>
          <button 
            className="bg-white border border-blue-300 hover:bg-blue-300 hover:text-white rounded-lg text-blue-500 text-xs py-2 px-4 mt-2 transition-colors duration-300"
            onClick={() => handleUpdateBookshelf(book.bookId)}
          >
            읽은 책으로
          </button>
        </div>
      ))}
    </div>
  );
};

export default NotRead;
