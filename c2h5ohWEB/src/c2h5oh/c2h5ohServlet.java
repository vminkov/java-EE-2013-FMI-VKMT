package c2h5oh;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2h5oh.jpa.Product;

import com.ProductBean;

@WebServlet("/c2h5ohServlet")
public class c2h5ohServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ProductBean productBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public c2h5ohServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Product> productList = productBean.listProducts();
		System.out.println("GPEL productList size: " + productList.size());
		request.setAttribute("productList", productList);
		request.getSession().setAttribute("productBean", productBean);
		request.getRequestDispatcher("/productList.jsp").forward(request,
				response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		System.out.println("GPEL post edit name: " + name);
		String price = request.getParameter("price");
		System.out.println("GPEL post edit price: " + price);
		Product product = null;
		if (id != null) {
			System.out.println("GPEL post edit id: " + id);
			product = productBean.getProduct(Long.parseLong(id));
			product.setName(name);
			product.setPrice(price);
			productBean.update(product);
		} else {
			productBean.newProduct(name, price);
		}

		List<Product> productList = productBean.listProducts();
		System.out.println("GPEL productList POST size: " + productList.size());
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/productList.jsp").forward(request,
				response);

	}

}
