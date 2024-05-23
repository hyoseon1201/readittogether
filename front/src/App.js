import React from "react";
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import PageLayout from "./components/navbar/PageLayout";

import { AuthProvider } from "./authentication/AuthContext";
import PrivateRoute from "./authentication/routers/PrivateRouter";
import PublicRoute from "./authentication/routers/PublicRouter";

import Library from "./pages/library/Library";
import Login from "./pages/accounts/Login";
import FindPassword from "./pages/accounts/FindPassword";
import Bookshelf from "./pages/bookshelf/Bookshelf";
import Profile from "./pages/accounts/Profile";
import RecommendBook from "./pages/recomend/RecommendBook";
import DetailBook from "./pages/recomend/DetailBook";
import Logout from "./api/accounts/Logout";

import SignUp from "./pages/accounts/SignUp";
import Diary from "./components/modal/Diary/Diary";
import ModifyProfile from "./pages/accounts/ModifyProfile";

import SearchPage from "./pages/recomend/SearchPage";

import LoginCallBack from "./components/auth_login/LoginCallback";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <PageLayout>
          <Routes>
            <Route element={<PublicRoute />}>
              <Route path="/" element={<Login />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/find-password" element={<FindPassword />} />
            </Route>
            <Route path="/ktest" element={<LoginCallBack />} />
              <Route path="/bookshelf/:memberId" element={<Bookshelf />} />
              <Route path="/detail-book/:bookId" element={<DetailBook />} />
              <Route path="/:memberId" element={<Library />} />
              <Route path="/recommend-book" element={<RecommendBook />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/modify/:memberId" element={<ModifyProfile />} />
              <Route path="/profile/:memberId" element={<Profile />} />
              <Route path="/diary/:memberId" element={<Diary />} />
            <Route element={<PrivateRoute />}>
              <Route path="/logout" element={<Logout />} />
            </Route>
          </Routes>
        </PageLayout>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
