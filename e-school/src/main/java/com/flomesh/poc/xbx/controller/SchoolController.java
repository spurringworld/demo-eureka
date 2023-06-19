package com.flomesh.poc.xbx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flomesh.poc.xbx.vo.HelloVO;
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
	public List<SchoolVO> getSchools(){
		System.out.println("======>>> get schools");
		List<SchoolVO> list = new ArrayList<SchoolVO>();
		list.add(new SchoolVO(1, "高新实验学校","大连高新园区小平岛128号"));
		list.add(new SchoolVO(2, "大连第24中学", "大连中山区长江路55号"));
		list.add(new SchoolVO(3, "大连第23中心--", "大连甘井子区泉水街19号"));
		list.add(new SchoolVO(4, "大连王府高中--", "大连天地软件园26号"));
		// if(schoolId > 0){
		// 	return list.stream().filter(s->s.getId()==schoolId).collect(Collectors.toList());
		// }
		return list;
	}

	@GetMapping("schools/{id}")
	public SchoolVO getSchoolById(@PathVariable(value="id") int id){
		List<SchoolVO> list = this.getSchools();
		for (SchoolVO schoolVO : list) {
			if(id == schoolVO.getId()) {
				return schoolVO;
			}
		}
		return null;
	}


	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("schools/{id}/students")
	public SchoolVO getStudents4School(@PathVariable(value="id") int id){
		System.out.println("======>>> get students for a school");
		SchoolVO sVo = this.getSchoolById(id);
		if(sVo != null){
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
		try { 
			System.out.println(new Date() + "\n"); 
			Thread.sleep(1000*3);   // 休眠3秒
			System.out.println(new Date() + "\n"); 
		 } catch (Exception e) { 
			 System.out.println("Got an exception!"); 
		 }
		SchoolVO sVo = this.getSchoolById(id);
		if(sVo != null){
			ServiceInstance serviceInstance = loadBalancerClient.choose("f-teacher");
			String url = "http://" + serviceInstance.getHost() + ":" 
				            + serviceInstance.getPort() 
							+ "/teachers?schoolId=" + id;
			System.out.println(url);
			List<TeacherVO> teachers = (List<TeacherVO>) restTemplate.getForObject(url, List.class);
			sVo.setTeachers(teachers);
		}
		return sVo;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("schools/teachers/hello")
	public String sayHello2Teacher(@RequestBody HelloVO helloMsg){
		System.out.println("======>>> say Hello from schools svc...");
		ServiceInstance serviceInstance = loadBalancerClient.choose("f-teacher");
		String url = "http://" + serviceInstance.getHost() + ":" 
				+ serviceInstance.getPort() 
				+ "/sayHello?teacherId=" + helloMsg.getTid() + "&msg=" + helloMsg.getMsg();
		System.out.println(url);
		return restTemplate.getForObject(url, String.class);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("schools/test/hello")
	public String testTeacher(){
		System.out.println("======>>> test from schools svc...");
		// call teacher
		ServiceInstance serviceInstance = loadBalancerClient.choose("f-teacher");
		String url = "http://" + serviceInstance.getHost() + ":" 
				+ serviceInstance.getPort() 
				+ "/sayHello?teacherId=3&msg=testttt";
		System.out.println(url);
		restTemplate.getForObject(url, String.class);
		// call student students/{id}/scores
		ServiceInstance studentSvc = loadBalancerClient.choose("c-student");
		String studentUrl = "http://" + studentSvc.getHost() + ":"  + studentSvc.getPort() 
				+ "/sayHello";
		System.out.println(studentUrl);
		restTemplate.getForObject(url, String.class);

		return "success!!!";
	}

}