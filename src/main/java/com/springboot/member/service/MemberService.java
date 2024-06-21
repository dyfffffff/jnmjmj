package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        // TODO should business logic
        verifyExistEmail(member.getEmail());
        return memberRepository.save(member);

    }

    public Member updateMember(Member member) {
        // TODO should business logic
        //회원이 진짜있는 회원인지 확인해야해서 멤버아이디로 검증필요
        Member findMember = findVeriftedMember(member.getMemberId());
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(status -> findMember.setMemberStatus(status));

        findMember.getModifiedAt(LocalDateTime.now());
        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);

    }

    public Member findMember(long memberId) {
        // TODO should business logic
        return findVeriftedMember(memberId);

    }

    public Page<Member> findMembers(int page, int size) {
        // TODO should business logic
        Pageable pageable = PageRequestage.of(page, size, Sort.by("memberId").descending());
        return memberRepository.findAll(pageable);

    }

    public void deleteMember(long memberId) {
        // TODO should business logic

        Member findMember = findVeriftedMember(memberId);

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);
        memberRepository.save(findMember);

    }

    public void findVerifiedMember(Long memberId) {
    }

    private void verifyExistEmail(String email) {
        //email이 데이테베이스에 존재하는지 확인
       Optional<Member>findByMember =  MemberRepository.findByEmail(email);
       if (findByMember.isPresent()) {
           throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
       }
    }

    private Member findVeriftedMember(long memberId){
        Optional<Member> findMember = memberRepository.findById(memberId);
       return findMember.orElseThrow(() ->
               new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        }
    }
}
