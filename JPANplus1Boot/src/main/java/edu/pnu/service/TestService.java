package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.MemberDTO;
import edu.pnu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestService {

	private final MemberRepository memberRepo;

	public List<MemberDTO> member() {
		List<Member> list = memberRepo.findAll();
		List<MemberDTO> retlist = new ArrayList<>();
		for (Member m : list) {
			retlist.add(MemberDTO.of(m));
		}
		return retlist;
	}

	public List<MemberDTO> member1() {
		List<Member> list = memberRepo.findMemberByFJ();
		List<MemberDTO> retlist = new ArrayList<>();
		for (Member m : list) {
			retlist.add(MemberDTO.of(m));
		}
		return retlist;
	}
	
	public List<MemberDTO> member2() {
		List<Member> list = memberRepo.findMemberByEG();
		List<MemberDTO> retlist = new ArrayList<>();
		for (Member m : list) {
			retlist.add(MemberDTO.of(m));
		}
		return retlist;
	}
	
	public List<MemberDTO> member3() {
		List<Member> list = memberRepo.findAll();
		List<MemberDTO> retlist = new ArrayList<>();
		for (Member m : list) {
			retlist.add(MemberDTO.of(m));
		}
		return retlist;
	}		
}
