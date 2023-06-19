package com.flomesh.poc.xbx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flomesh.poc.xbx.vo.TeacherVO;

 
@RestController
public class TeacherController {

	@Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;
 
	@Value("${service-g.url}")
	private String urlServiceG;


	@GetMapping("teachers")
	public List<TeacherVO> getTeachers(@RequestParam(value="schoolId", required=false, defaultValue="0") int schoolId ){
		System.out.println("======>>> get teachers");
		try { 
			System.out.println(new Date() + "\n"); 
			Thread.sleep(1000*1);   // 休眠3秒
			System.out.println(new Date() + "\n"); 
		 } catch (Exception e) { 
			 System.out.println("Got an exception!"); 
		 }
		List<TeacherVO> list = new ArrayList<TeacherVO>();
		list.add(new TeacherVO(1, "孔子","高级",55,2, "kongzi@test.com"));
		list.add(new TeacherVO(2, "莫言","高级",44,2, "moyan@test.com"));
		list.add(new TeacherVO(3, "钱学森","特级",39,1, "qianxs@test.com"));
		list.add(new TeacherVO(4, "李淑婷","中级",32,1, "lishuting@test.com"));
		list.add(new TeacherVO(5, "张爱玲","高级",32,1, "zhangailing@test.com"));
		if(schoolId > 0){
			return list.stream().filter(s->s.getSchoolId()==schoolId).collect(Collectors.toList());
		}
		return list;
	}

	@GetMapping("teachers/{id}")
	public TeacherVO getTeacherById(@PathVariable(value="id") int id){
		List<TeacherVO> list = this.getTeachers(0);
		for (TeacherVO teacher : list) {
			if(id == teacher.getId()) {
				return teacher;
			}
		}
		return null;
	}

	// @GetMapping("teachers/{id}/subject")
	// public TeacherSubjectVO getClub4Student(@PathVariable(value="id") int id){
	// 	System.out.println("======>>> get teachers with subject");
	// 	TeacherSubjectVO result = new TeacherSubjectVO();
	// 	List<TeacherVO> list = this.getTeachers(0);
	// 	for (TeacherVO teacher : list) {
	// 		if(id == teacher.getId()) {
	// 			result.setTeacher(teacher);
	// 			// 获取科目
	// 			ServiceInstance serviceInstance = loadBalancerClient.choose("g-subject");
	// 			String url = "http://" + serviceInstance.getHost() + ":" 
	// 			            + serviceInstance.getPort() 
	// 						+ "teachers/" + id + "/subject";
	// 			System.out.println(url);
	// 			SubjectVO subject = restTemplate.getForObject(url, SubjectVO.class);
	// 			result.setSubject(subject);
	// 		}
	// 	}
	// 	return result;
	// }


	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("sayHello")
	public String sayHello(
		@RequestParam(value="teacherId", required=true, defaultValue="0") int teacherId,
		@RequestParam(value="msg", required=true) String msg) {
		System.out.println("======>>> sayHello");
		TeacherVO teacher = this.getTeacherById(teacherId);
		if(teacher == null) {
			return "非法教师信息";
		}
		ServiceInstance serviceInstance = loadBalancerClient.choose("d-score");
		String url = "http://" + serviceInstance.getHost() + ":" 
				+ serviceInstance.getPort() 
				+ "/test/hello?teacher=" + teacher.getName() + "&msg=" + msg;
		System.out.println(url);
		return restTemplate.getForObject(url, String.class);
		// String url = this.urlServiceG + "/hello?msg=" + msg + "&teacher=" + teacher.getName();
		// System.out.println(url);
		// String msgFromServiceG = restTemplate.getForObject(url, String.class);
		// return msgFromServiceG;
	}

}