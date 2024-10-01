package edu.pnu.service;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	public boolean join(Member member) {
		memberRepository.save(member);
		return true;
	}
}
