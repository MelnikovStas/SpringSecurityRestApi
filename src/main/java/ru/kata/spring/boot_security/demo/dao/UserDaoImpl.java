package spring_mvc_boot.springBoot.SpringMVCApp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import spring_mvc_boot.springBoot.SpringMVCApp.models.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;


    public List<User> index() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User show(int id) {
        return em.find(User.class, id);
    }

    public void save(User user) {
        em.persist(user);
    }

    public void update(int id, User updateUser) {
        User user = em.find(User.class, id);
        user.setName(updateUser.getName());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());
        em.merge(user);
    }

    public void delete(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

}
