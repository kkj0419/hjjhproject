package hello.hellospring.config;

//import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
//import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.repository.PostDao;
import hello.hellospring.repository.PostRepository;
import hello.hellospring.service.MemberService;
import hello.hellospring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    //       스프링 데이터 JPA . SpringDataJpaMemberRepository
    private final MemberRepository memberRepository;
//    private PostRepository postRepository;
//    private final PostDao postDao;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean//스프링 빈을 등록하겠다.
    public MemberService memberService() {

        return new MemberService(memberRepository);
    }

//    @Bean
//    public PostService postService(){
//        return new PostService(postRepository);
//    }
}










/*
    private final DataSource dataSource;
    private final EntityManager em;
    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }
    @Bean //스프링 빈으로 등록하겠다.
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
// return new MemoryMemberRepository();
// return new JdbcMemberRepository(dataSource);
// return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
*/

