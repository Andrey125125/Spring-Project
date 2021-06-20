package ua.com.library.filters;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.library.service.OrderService;

import javax.servlet.*;

import java.io.IOException;


//@AllArgsConstructor()
//public class OrderLimitFilter implements Filter{
//    private final OrderService orderService;
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//
//        if( orderService.allowUserOrder() ) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//
//
//
//    }
//
//
//}
//
//
//
//@Configuration
//class FilterConfig {
//    private OrderService OrderService;
//
//    @Bean
//    public FilterRegistrationBean<OrderLimitFilter> orderFilter(){
//        FilterRegistrationBean<OrderLimitFilter> registrationBean
//                = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new OrderLimitFilter( OrderService));
//        registrationBean.addUrlPatterns("/reader");
//        registrationBean.addUrlPatterns("/reader/order/*");
//
//        return registrationBean;
//    }
//}



