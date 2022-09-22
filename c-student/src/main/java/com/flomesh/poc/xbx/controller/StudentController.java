package com.flomesh.poc.xbx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flomesh.poc.xbx.vo.ClubVO;
import com.flomesh.poc.xbx.vo.ScoreVO;
import com.flomesh.poc.xbx.vo.StudentClubVO;
import com.flomesh.poc.xbx.vo.StudentScoreVO;
import com.flomesh.poc.xbx.vo.StudentVO;

 
@RestController
public class StudentController {
	@Autowired
    DiscoveryClient discoveryClient;

	@Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

	@Value("${cluster1.gateway.url}")
	private String cluster1Gateway;
 
	@GetMapping("students")
	public List<StudentVO> getStudents(@RequestParam(value="schoolId", required=false, defaultValue="0") int schoolId ){
		System.out.println("======>>> get students");
		List<StudentVO> list = new ArrayList<StudentVO>();
		list.add(new StudentVO(1, "张三",13,1));
		list.add(new StudentVO(2, "李四",13,1));
		list.add(new StudentVO(3, "赵五",14,2));
		list.add(new StudentVO(4, "孙六",13,2));
		if(schoolId > 0){
			return list.stream().filter(s->s.getSchoolId()==schoolId).collect(Collectors.toList());
		}
		return list;
	}


	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("students/{id}/scores")
	public StudentScoreVO getScore4Student(@PathVariable(value="id") int id){
		System.out.println("======>>> get students with scores");
		
		StudentScoreVO ssVo = new StudentScoreVO();
		List<StudentVO> list = this.getStudents(0);
		for (StudentVO student : list) {
			if(id == student.getId()) {
				ssVo.setStudent(student);
				String url = this.cluster1Gateway + "/api-d/scores?studentId=" + id;
				System.out.println(url);
				final List<ScoreVO> scores = (List<ScoreVO>) restTemplate.getForObject(url, List.class);
				ssVo.setScores(scores);
			}
		}
		return ssVo;
	}

	@GetMapping("students/{id}/club")
	public StudentClubVO getClub4Student(@PathVariable(value="id") int id){
		System.out.println("======>>> get students with club");
		// String services = "Services: " + discoveryClient.getServices();
        // System.out.println(services);
		StudentClubVO result = new StudentClubVO();
		List<StudentVO> list = this.getStudents(0);
		for (StudentVO student : list) {
			if(id == student.getId()) {
				result.setStudent(student);
				// 获取club
				ServiceInstance serviceInstance = loadBalancerClient.choose("c1-club");
				String url = "http://" + serviceInstance.getHost() + ":" 
				            + serviceInstance.getPort() 
							+ "students/" + id + "/club";
				System.out.println(url);
				ClubVO club = restTemplate.getForObject(url, ClubVO.class);
				result.setClub(club);
			}
		}
		return result;
	}

}