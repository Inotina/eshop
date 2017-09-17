package by.enot.eshop.controller;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductDao productDao;
    private static final Logger log = Logger.getLogger(ProductController.class);


    @Transactional
    @RequestMapping(value = "/")
    public ModelAndView home(){
        List<Product> productList = productDao.getAll();
        ModelAndView model = new ModelAndView("home");
        model.addObject("productList", productList);
        return model;
    }

    @Transactional
    @RequestMapping(value = "/product")
    public ModelAndView product(@RequestParam("id") long id) {
        ModelAndView model;
        try {
            Product product = productDao.getById(id);
            model = new ModelAndView("product");
            model.addObject("Product", product);
            log.debug("rendering page product id: " + id);
        }catch (NoSuchEntityInDBException e){
            log.info(e);
            return new ModelAndView("redirect:/");
        }
        return model;
    }
}
