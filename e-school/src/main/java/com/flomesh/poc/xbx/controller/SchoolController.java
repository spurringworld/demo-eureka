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

import com.flomesh.poc.xbx.vo.SchoolVO;
import com.flomesh.poc.xbx.vo.StudentVO;
import com.flomesh.poc.xbx.vo.TeacherVO;

 
@RestController
public class SchoolController {
	@Autowired
    DiscoveryClient discoveryClient;

	@Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

	@Value("${cluster2.gateway.url}")
	private String cluster2Gateway;

	@GetMapping("schools")
	public List<SchoolVO> getSchools(@RequestParam(value="schoolId", required=false, defaultValue="0") int schoolId ){
		System.out.println("======>>> get schools");
		List<SchoolVO> list = new ArrayList<SchoolVO>();
		list.add(new SchoolVO(1, "高新实验学校","大连高新园区128号"));
		list.add(new SchoolVO(2, "博文中学", "大连中山区55号"));
		if(schoolId > 0){
			return list.stream().filter(s->s.getId()==schoolId).collect(Collectors.toList());
		}
		return list;
	}


	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("schools/{id}/students")
	public SchoolVO getStudents4School(@PathVariable(value="id") int id){
		System.out.println("======>>> get students for a school");
		SchoolVO sVo = null;
		List<SchoolVO> list = this.getSchools(id);
		if(list!=null && list.size()>0){
			sVo = list.get(0);
			String url = this.cluster2Gateway + "/api-c/students?schoolId=" + id;
			System.out.println(url);
			List<StudentVO> students = (List<StudentVO>) restTemplate.getForObject(url, List.class);
			sVo.setStudents(students);
		}
		
		return sVo;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("schools/{id}/teachers")
	public SchoolVO getTeachers4School(@PathVariable(value="id") int id){
		System.out.println("======>>> get teachers for a school");
		SchoolVO sVo = null;
		List<SchoolVO> list = this.getSchools(id);
		if(list!=null && list.size()>0){
			sVo = list.get(0);

			ServiceInstance serviceInstance = loadBalancerClient.choose("f-teacher");
			String url = "http://" + serviceInstance.getHost() + ":" 
				            + serviceInstance.getPort() 
							+ "teachers?schoolId=" + id;
			System.out.println(url);
			List<TeacherVO> teachers = (List<TeacherVO>) restTemplate.getForObject(url, List.class);
			sVo.setTeachers(teachers);
		}
		
		return sVo;
	}

}