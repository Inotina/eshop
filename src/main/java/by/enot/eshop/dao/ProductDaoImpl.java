package by.enot.eshop.dao;

import by.enot.eshop.entity.Product;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private SessionFactory sesionFactory;
    private static final Logger log = Logger.getLogger(ProductDaoImpl.class);

    public ProductDaoImpl(SessionFactory sesionFactory) {
        this.sesionFactory = sesionFactory;
    }

    @Transactional
    public List<Product> getAll() {
        Session session = getSession();
        CriteriaQuery<Product> cq = session.getCriteriaBuilder().createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        List<Product> list = session.createQuery(cq).getResultList();
        log.debug("all products selected");
        return list;
    }
    @Transactional
    public Product getById(int id) throws NoSuchEntityInDBException {
        Product product = getSession().find(Product.class, id);
        if (product == null){
            log.debug(id + " - no product with this id in db.");
            throw new NoSuchEntityInDBException("No entity with id: " + id);
        }
        log.debug("id: " + id + " selected from db.");
        return product;
    }

    @Transactional
    public Product getByName(String name) throws NoSuchEntityInDBException {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<Product> cq = builder.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        cq.where(builder.equal(root.get("name"), name));
        List<Product> list = getSession().createQuery(cq).getResultList();
        if (list.size() == 0){
            log.debug(name + " - no product with this name in db.");
            throw new NoSuchEntityInDBException();
        }
        log.debug("name: " + name + " selected from db.");
        return list.get(0);
    }

    public void add(Product product) {
        getSession().persist(product);
        log.info("new product was added with name - " + product.getName());
    }

    public void update(Product product) {
        getSession().merge(product);
        log.info("product with id - " + product.getId() + " was updated");
    }

    public void delete(Product product) {
        getSession().delete(product);
        log.info("product with id - " + product.getId() + " and name - " + product.getName() + " was deleted.");
    }

    private Session getSession(){
        return sesionFactory.getCurrentSession();
    }
}
