import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { handleSignUp } from "../../api/accounts/SignUp";
import { checkEmailDuplicate } from "../../api/accounts/MailDuplicate";
import { checkNicknameDuplicate } from "../../api/accounts/NicknameDuplicate";
import SendCode from "../../api/accounts/SendCode";
import CheckCode from "../../api/accounts/CheckCode";

const SignUp = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [emailCode, setEmailCode] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [nickname, setNickname] = useState('');
  const [name, setName] = useState('');
  const [birth, setBirth] = useState('');
  const [gender, setGender] = useState('');
  // 이메일 인증 결과 상태
  const [verified, setVerified] = useState(false);

  const [emailMessage, setEmailMessage] = useState('');
  const [emailStatusMessage, setEmailStatusMessage] = useState('');
  const [emailStatusMessageClassName, setEmailStatusMessageClassName] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');
  const [passwordConfirmMessageClassName, setPasswordConfirmMessageClassName] = useState(''); // 글자 색 바꾸기 위함
  const [nicknameMessage, setNicknameMessage] = useState('');
  const [nicknameStatusMessage, setNicknameStatusMessage] = useState('');
  const [nicknameStatusMessageClassName, setNicknameStatusMessageClassName] = useState('');

  // 가입하기 버튼 활성화
  const [isFormValid, setIsFormValid] = useState(false);
  // 중복 확인을 안하면 가입하기 버튼 활성화 안됨
  const [isEmailValid, setIsEmailValid] = useState(false);
  const [isNicknameValid, setIsNicknameValid] = useState(false);
  // 이메일 코드 확인
  const [codeVerification, setCodeVerification] = useState('');
  const [codeVerificationClassName, setCodeVerificationClassName] = useState('');

  // 중복 확인 버튼
  const [isCorrectEmail, setIsCorrectEmail] = useState(false);
  const [isCorrectNickname, setIsCorrectNickname] = useState(false);
   // 이메일 중복 확인이 완료되었는지 여부
   const [isEmailCheckDone, setIsEmailCheckDone] = useState(false);

  useEffect(() => {
    // 모든 입력란이 채워졌는지 + 중복 검사를 했는지 + 이메일 인증코드를 받았는지 확인
    const emailRegExp = /^[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?$/;
    const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    const nicknameRegExp = /^[가-힣a-zA-Z0-9]{2,8}$/;

    const isValid = email && emailRegExp.test(email) && password && passwordConfirm && (password === passwordConfirm) && passwordRegExp.test(password) && passwordRegExp.test(passwordConfirm) && nickname && nicknameRegExp.test(nickname) && birth && gender && isEmailValid && isNicknameValid && verified;
    setIsFormValid(isValid);
  }, [email, password, passwordConfirm, nickname, birth, gender, isEmailValid, isNicknameValid, verified]);

  useEffect(() => {
    const emailRegExp = /^[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?$/;
    const isCorrect = emailRegExp.test(email);
    setIsCorrectEmail(isCorrect);
  }, [email]);

  useEffect(() => {
    const nicknameRegExp = /^[가-힣a-zA-Z0-9]{2,8}$/;
    const isCorrect = nicknameRegExp.test(nickname);
    setIsCorrectNickname(isCorrect);
  }, [nickname]);

  const onChangeEmail = (e) => {
    const currentEmail = e.target.value;
    setIsEmailValid(false);
    setEmail(currentEmail);
    setIsEmailCheckDone(false); // 이메일이 바뀌면 중복 확인도 초기화
    const emailRegExp = /^[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?$/;

    if (!emailRegExp.test(currentEmail) && currentEmail.length !== 0) {
      setEmailMessage('올바른 형식이 아닙니다!')
    } else {
      setEmailMessage('')
    };
  };
  
  useEffect(() => {
    setEmailStatusMessage(''); // 이메일 상태 메시지 초기화
  }, [email]); // email 상태가 변경될 때마다 useEffect 호출  
  
  const onChangeNickname = (e) => {
    const currentNickname = e.target.value;
    // setEmailStatusMessage('');
    setIsNicknameValid(false);
    setNickname(currentNickname);
    const nicknameRegExp = /^[가-힣a-zA-Z0-9]{2,8}$/;
    
    if (!nicknameRegExp.test(currentNickname) && currentNickname.length !== 0) {
      setNicknameMessage('올바른 형식이 아닙니다!')
    } else {
      setNicknameMessage('')
    };
  };

  useEffect(() => {
    setNicknameStatusMessage('');
  }, [nickname]);

  const onChangePassword = (e) => {
    const currentPassword = e.target.value;
    setPassword(currentPassword);
    const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    if (!passwordRegExp.test(currentPassword) && currentPassword.length !== 0) {
      setPasswordMessage('올바른 형식이 아닙니다!')
    } else {
      setPasswordMessage('')
    };
  };
  
  const onChangePasswordConfirm = (e) => {
    const currentPasswordConfirm = e.target.value;
    const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    setPasswordConfirm(currentPasswordConfirm);
    if (password !== currentPasswordConfirm) {
      setPasswordConfirmMessage('비밀번호가 일치하지 않습니다!');
      setPasswordConfirmMessageClassName('text-sm text-red-500');
    } else if (!passwordRegExp.test(currentPasswordConfirm)) {
      setPasswordConfirmMessage('');
    } else {
      setPasswordConfirmMessage('비밀번호가 일치합니다.');
      setPasswordConfirmMessageClassName('text-sm text-blue-500');
    };
  };

  useEffect(() => {
    setPasswordConfirmMessage('');
  }, [password]);

  let now = new Date();
  let years = []
  for (let y = now.getFullYear(); y >= 1930; y -= 1) {
    years.push(y)
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await handleSignUp(name, email, password, nickname, birth, gender);
      console.log(response);
      alert('회원가입에 성공했습니다.');
      navigate("/login");
    } catch (error) {
      throw error;
    };
  };

  const handleCheckEmail = async () => {
    try {
      const isEmailDuplicate = await checkEmailDuplicate(email);
      setIsEmailValid(isEmailDuplicate);
      setEmailStatusMessage(!isEmailDuplicate ? '중복된 이메일입니다.' : '사용 가능한 이메일입니다.');
      setEmailStatusMessageClassName(!isEmailDuplicate ? 'text-sm text-red-500' : 'text-sm text-blue-500');
      
      setIsCorrectEmail(isEmailDuplicate);
      setIsEmailCheckDone(true);
    } catch (error) {
      console.error(error);
    };
  };

  const handleSendEmailCode = async () => {
    try {
      alert('이메일을 확인해주세요. 인증 코드는 10분동안 유효합니다.')
      if (isEmailCheckDone) {
        if (isCorrectEmail) {
          await SendCode(email); // 인증 코드 전송
        } else {
          alert('이메일 전송에 실패했습니다');
        }
      } else {
        alert('이메일 중복 확인을 먼저 진행해주세요');
      }
    } catch (error) {
      console.error(error);
    }
  }

  const handleCheckEmailCode = async () => {
    try {
      const result = await CheckCode(email, emailCode);
      setCodeVerificationClassName(result ? 'text-sm text-blue-500' : 'text-sm text-red-500');
      setCodeVerification(result ? '인증되었습니다' : '인증에 실패했습니다');
      setVerified(true);
    } catch (error) {
      setCodeVerificationClassName('text-sm text-red-500');
      setCodeVerification('인증에 실패했습니다');
      console.error(error);
    };
  };

  const handleCheckNickname = async () => {
    try {
      const isNicknameDuplicate = await checkNicknameDuplicate(nickname);
      setIsNicknameValid(isNicknameDuplicate);
      setNicknameStatusMessage(!isNicknameDuplicate ? '중복된 닉네임입니다.' : '사용 가능한 닉네임입니다.');
      setNicknameStatusMessageClassName(!isNicknameDuplicate ? 'text-sm text-red-500' : 'text-sm text-blue-500');
    } catch (error) {
      console.log(error);
    };
  };

  return (
    <div className="max-w-md mx-auto my-8 p-4 border border-gray-300 rounded shadow">
      <div className="flex justify-center">
        <h2 className="text-2xl mb-4">회원가입</h2>
      </div>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="name" className="block mb-1">이름</label>
          <input type="text" id="name" name="name" value={name} onChange={(e) => setName(e.target.value)} className="border border-gray-300 px-2 py-1 w-full" />
        </div>
        <div className="mb-4">
          <div className="flex justify-between">
            <label htmlFor="email" className="block mb-1">이메일</label>
            <p className="text-red-500 mr-32 text-sm">{emailMessage}</p>
            <p className={emailStatusMessageClassName}>{emailStatusMessage}</p>
          </div>
          <div className="flex items-center">
            <input type="text" id="email" name="email" value={email} onChange={onChangeEmail} className="border border-gray-300 px-2 py-1 flex-grow mr-3" />
            {isEmailCheckDone ? (
              <button type="button" onClick={handleSendEmailCode} className={`bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded ml-2 ${!isCorrectEmail && 'opacity-50 cursor-not-allowed'}`} disabled={!isCorrectEmail}>인증코드 전송</button>
            ) : (
              <button type="button" onClick={handleCheckEmail} className={`bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded ml-2 ${!isCorrectEmail && 'opacity-50 cursor-not-allowed'}`} disabled={!isCorrectEmail}>중복 확인</button>
            )}
          </div>
        </div>
        <div className="mb-4">
          <div className="flex justify-between">
            <label htmlFor="email" className="block mb-1">인증코드 확인</label>
          </div>
          <div className="flex items-center">
            <input type="text" id="emailCertificate" name="emailCode" value={emailCode} onChange={(e) => setEmailCode(e.target.value)} className="border border-gray-300 px-2 py-1 flex-grow mr-3" />
            <button type="button" onClick={handleCheckEmailCode} className="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded ml-2">인증코드 확인</button>
          </div>
          <p className={codeVerificationClassName}>{codeVerification}</p>
        </div>
        <div className="mb-4">
          <div className="flex justify-between">
            <label htmlFor="password" className="block mb-1">비밀번호</label>
            <p className="text-red-500 text-sm">{passwordMessage}</p>
          </div>
          <input type="password" id="password" name="password" value={password} onChange={onChangePassword} className="border border-gray-300 px-2 py-1 w-full" />
          <p className="text-sm text-gray-500">영문·숫자·특수문자를 포함한 8~20자</p>
        </div>
        <div className="mb-4">
          <div className="flex justify-between">
            <label htmlFor="passwordConfirm" className="block mb-1">비밀번호 확인</label>
            <p className={passwordConfirmMessageClassName}>{passwordConfirmMessage}</p>
          </div>
          <input type="password" id="passwordConfirm" name="passwordConfirm" value={passwordConfirm} onChange={onChangePasswordConfirm} className="border border-gray-300 px-2 py-1 w-full" />
        </div>
        <div className="mb-4">
          <div className="flex justify-between">
            <label htmlFor="nickname" className="block mb-1">닉네임</label>
            <p className="text-red-500 mr-32 text-sm">{nicknameMessage}</p>
            <p className={nicknameStatusMessageClassName}>{nicknameStatusMessage}</p>
          </div>
          <div className="flex items-center">
            <input type="text" id="nickname" name="nickname" value={nickname} onChange={onChangeNickname} className="border border-gray-300 px-2 py-1 flex-grow mr-3" />
            <button type='button' onClick={handleCheckNickname} className={`bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded ml-2 ${!isCorrectNickname && 'opacity-50 cursor-not-allowed'}`} disabled={!isCorrectNickname}>중복 확인</button>
          </div>
            <p className="text-sm text-gray-500">한글·영문·숫자를 포함한 2~8자(공백 허용X)</p>
        </div>
        <div className="mb-4">
          <label htmlFor="birth" className="block mb-1">출생년도</label>
          <select id='birth' name='birth' value={birth} onChange={(e) => setBirth(e.target.value)} className="border border-gray-300 px-2 py-1 w-full">
            <option value="">---선택---</option>
            {years.map((year, index) => (
              <option key={index}>{year}</option>
            ))}
          </select>
        </div>
        <div className="mb-4">
          <label className="block mb-1">성별</label>
          <div className="flex">
            <label className="mr-4"><input type="radio" name="gender" value="0" onChange={(e) => setGender(e.target.value)} className="mr-1 ml-1" /> 남자</label>
            <label className="mr-4"><input type="radio" name="gender" value="1" onChange={(e) => setGender(e.target.value)} className="mr-1 ml-1" /> 여자</label>
          </div>
        </div>
        <div className="flex justify-center">
          <button type="submit" className={`bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 ${!isFormValid && 'opacity-50 cursor-not-allowed'}`} disabled={!isFormValid}>가입하기</button>
        </div>
      </form>
    </div>
  );
}

export default SignUp;