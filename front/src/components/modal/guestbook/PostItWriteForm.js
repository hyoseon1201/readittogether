import React from "react";

const WriteFormModal = ({
  postItContent,
  setPostItContent,
  handleSubmit,
  handleCloseAll,
  refreshList
}) => {
  return (
    <div
      className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 overflow-y-auto h-full w-full z-40"
      onClick={handleCloseAll}
    >
      <div
        className="p-5 border w-96 h-96 shadow-lg bg-yellow-200 flex flex-col"
        onClick={(e) => e.stopPropagation()}
      >
        <form onSubmit={handleSubmit} className="flex flex-col h-full">
          <textarea
            value={postItContent}
            onChange={(e) => setPostItContent(e.target.value)}
            className="w-full customFont text-xl flex-1 p-2 bg-yellow-200 resize-none border-none outline-none"
            maxLength={200}
          />
          <button
            type="submit"
            className="self-end mt-2 px-4 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-400 transition-colors"
          >
            방명록 남기기
          </button>
        </form>
      </div>
    </div>
  );
};

export default WriteFormModal;
