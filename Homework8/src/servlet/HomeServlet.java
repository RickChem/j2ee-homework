package servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import service.HomeService;
import util.MessageState;
import util.Result;
import util.ServletUtil;
import util.Statistic;
import vo.CourseListVO;
import vo.CourseUserVO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by raychen on 2016/12/11.
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static ApplicationContext applicationContext;
    private static HomeService homeService;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        homeService = (HomeService) applicationContext.getBean("HomeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        ServletContext context = getServletContext();
        CourseListVO courseListVO = new CourseListVO();
        Result res = new Result();
        if (session.getAttribute("user_id") != null) {
            int user_id = (int) session.getAttribute("user_id");
            ArrayList<CourseUserVO> courseUserVOs = homeService.getUserCourses(user_id);
            courseListVO.setCourses(courseUserVOs);
            session.removeAttribute("res");
            for (CourseUserVO vo: courseUserVOs) {
                if (vo.getState().equals("缺考")){
                    System.out.println("in to ");
                    res.setState(MessageState.FAIL);
                    break;
                }
            }
        }
        session.setAttribute("courses", courseListVO);
        session.setAttribute("res", res);
        Statistic statistic = new Statistic();
        statistic.setLogin((String) context.getAttribute("login_count"));
        statistic.setVisitor((String) context.getAttribute("visitor_count"));
        session.setAttribute("state", statistic);
        context.getRequestDispatcher("/jsp/Home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        ServletContext context = getServletContext();
        if (req.getParameter("logout") != null) {
            System.out.println("注销会话");
            session.invalidate();
            session = null;
            ServletUtil.decContext(context, "login_count");
            resp.sendRedirect("/login");
        } else if (req.getParameter("login") != null) {
            ServletUtil.decContext(context, "visitor_count");
            resp.sendRedirect("/login");
        } else {
            resp.sendRedirect("/home");
        }
    }
}
