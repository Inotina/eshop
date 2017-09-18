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

// update/delete/add new product. create table with all products
@Controller
public class AdminProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductService productService;

    //return the table with all products in db
    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    public ModelAndView onload(){
        ModelAndView model = new ModelAndView("admin/adminproduct");
        model.addObject("Products", productDao.getAll());
        model.addObject("newProduct", new Product());
        return model;
    }
    //fit the forms for editing selected by id product from table
    @RequestMapping(value = "/admin/products", method = RequestMethod.POST)
    public ModelAndView selected(@RequestParam("row")long productId){
        ModelAndView model = onload();
        try {
            model.addObject("targetp", productDao.getById(productId));
        } catch (NoSuchEntityInDBException e) {
            e.printStackTrace();
        }
        return model;
    }
    //make decision about updating/deleting product and return it to view(done or failed)
    @Transactional
    @RequestMapping(value = "/admin/productchange", method = RequestMethod.POST)
    public ModelAndView changeProduct(@ModelAttribute("targetp")Product product, @RequestParam("bt") String button, @RequestParam("id") long id,
                                        @RequestParam("oldname") String oldName) {
        ModelAndView model = new ModelAndView("admin/adminproduct");
        //code for update
        if (button.equals("Update")) {
            model.addObject("targetp", product);
            //check if all product field are valid
            if (productService.validateCurrentModification(product, oldName)) {
                product.setId(id);
                productDao.update(product);
                model.addObject("message", "Successfully updated");
            //if not valid add message on view what is wrong
            }else{
                model.addObject("errMessage", productService.getErrorMessage());
                productService.clearErrorMessage();
            }
        //code for delete
        }else if (button.equals("Delete")){
            product.setId(id);
            productDao.delete(product);
            model.addObject("message", "Successfully deleted");
            //remove info about deleted obj from view
            model.addObject("targetp", null);
        }
        //all products for main product table
        model.addObject("Products", productDao.getAll());
        model.addObject("newProduct", new Product());
        return model;
    }
    //adding new product
    @Transactional
    @RequestMapping(value = "/admin/productadd", method = RequestMethod.POST)
    public ModelAndView addProduct(@ModelAttribute("newProduct") Product product){
        ModelAndView model = new ModelAndView("admin/adminproduct");
        //check if all fields are valid
        if (productService.validate(product)){
            productDao.add(product);
            model.addObject("addMessage", "Successfully added!");
            model.addObject(("newProduct"), new Product());
        }else{
            //if not valid add message on view what is wrong
            model.addObject("errAddMessage", productService.getErrorMessage());
            productService.clearErrorMessage();
            model.addObject("newProduct", product);
        }
        //all products for main product table
        model.addObject("Products", productDao.getAll());
        return model;
    }
}
