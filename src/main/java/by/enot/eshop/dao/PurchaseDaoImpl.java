package by.enot.eshop.dao;

import by.enot.eshop.entity.Purchase;
import by.enot.eshop.exception.NoSuchEntityInDBException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PurchaseDaoImpl implements PurchaseDao{

    private SessionFactory sesionFactory;
    private static final Logger log = Logger.getLogger(PurchaseDaoImpl.class);

    public PurchaseDaoImpl(SessionFactory sessionFactory) {
        this.sesionFactory = sessionFactory;
    }
    @Transactional
    public List<Purchase> getAll() {
        Session session = getSession();
        CriteriaQuery<Purchase> cq = session.getCriteriaBuilder().createQuery(Purchase.class);
        Root<Purchase> root = cq.from(Purchase.class);
        cq.select(root);
        List<Purchase> list = session.createQuery(cq).getResultList();
        log.debug("all purchases selected.");
        return list;
    }
    @Transactional
    public Purchase getByID(long id) throws NoSuchEntityInDBException {
        Purchase purchase = getSession().find(Purchase.class, id);
        if (purchase == null){
            log.debug(purchase.getId() + " - no purchase with this id in db.");
            throw new NoSuchEntityInDBException("No purchase in db with id: " +purchase.getId());
        }
        log.debug("id " + purchase.getId() + " selected from db");
        return purchase;
    }

    public void add(Purchase purchase) {
        getSession().persist(purchase);
        log.info("new purchase was added from client - " + purchase.getClient().getId());
    }

    public void update(Purchase purchase) {
        getSession().update(purchase);
        log.info("purchase with id - " + purchase.getId() + " was updated.");
    }

    public void delete(Purchase purchase) {
        getSession().delete(purchase);
        log.info("purchase with id - " + purchase.getId() + " was deleted.");
    }

    private Session getSession(){
        return sesionFactory.getCurrentSession();
    }
}
