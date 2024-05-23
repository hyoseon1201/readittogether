package com.ssafy.rit.back.serviceImpl;

import com.ssafy.rit.back.dto.member.customDto.CustomUserDetails;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.exception.member.MemberWrongEmailException;
import com.ssafy.rit.back.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // findByEmail 로 Member 정보 가져올 때, 해당 유저가 null 일 경우의 예외 추가, 예외 통과 시 기존 CustomUserDetails 에 해당 Member 리턴
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member memberData = optionalMember.orElseThrow(() -> new UsernameNotFoundException("해당 유저는 존재하지 않습니다. Email: " + email));

        return new CustomUserDetails(memberData);
    }
}
