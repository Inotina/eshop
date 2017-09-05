package by.enot.eshop.controller.admin;

import by.enot.eshop.dao.ProductDao;
import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import by.enot.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
public class AdminProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    public ModelAndView onload(){
        ModelAndView model = new ModelAndView("admin/adminproduct");
        model.addObject("Products", productDao.getAll());
        model.addObject("newProduct", new Product());
        return model;
    }
    @RequestMapping(value = "/admin/products", method = RequestMethod.POST)
    public ModelAndView selected(@RequestParam("row")int productId){
        ModelAndView model = onload();
        try {
            model.addObject("targetp", productDao.getById(productId));
        } catch (NoSuchEntityInDBException e) {
            e.printStackTrace();
        }
        return model;
    }
    @Transactional
    @RequestMapping(value = "/admin/productchange", method = RequestMethod.POST)
    public ModelAndView changeProduct(@ModelAttribute("targetp")Product product, @RequestParam("bt") String button, @RequestParam("id") int id,
                                        @RequestParam("oldname") String oldName) {
        ModelAndView model = new ModelAndView("admin/adminproduct");
        if (button.equals("Update")) {
            model.addObject("targetp", product);
            if (productService.validateCurrentModification(product, oldName)) {
                product.setId(id);
                productDao.update(product);
                model.addObject("message", "Successfully updated");
            }else{
                model.addObject("errMessage", productService.getErrorMessage());
                productService.clearErrorMessage();
            }
        }else if (button.equals("Delete")){
            product.setId(id);
            productDao.delete(product);
            model.addObject("message", "Successfully deleted");
            //remove info about deleted obj from view
            model.addObject("targetp", null);
        }
        model.addObject("Products", productDao.getAll());
        model.addObject("newProduct", new Product());
        return model;
    }

    @Transactional
    @RequestMapping(value = "/admin/productadd", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("newProduct") Product product){
        ModelAndView model = new ModelAndView("admin/adminproduct");
        if (productService.validate(product)){
            productDao.add(product);
            model.addObject("addMessage", "Successfully added!");
            model.addObject(("newProduct"), new Product());
        }else{
            model.addObject("errAddMessage", productService.getErrorMessage());
            productService.clearErrorMessage();
            model.addObject("newProduct", product);
        }
        model.addObject("Products", productDao.getAll());
        return model;
    }
}
