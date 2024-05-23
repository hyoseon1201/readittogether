// PageLayout.js
import React from 'react';
import { useLocation } from 'react-router-dom';
import NavBar from './NavBar';

// 네브바와 사이드바를 숨기고 싶은 경로 목록
const hideLayoutPaths = ['/', '/login', '/signup', '/find-password'];

const PageLayout = ({ children }) => {
  const location = useLocation();
  const showLayout = !hideLayoutPaths.includes(location.pathname);

  return (
    <div>
      {showLayout && <NavBar />}
      {children}
    </div>
  );
};

export default PageLayout;
