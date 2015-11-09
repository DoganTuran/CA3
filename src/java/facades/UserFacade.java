package facades;

import entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class UserFacade {

    private final Map<String, User> users = new HashMap<>();
    private EntityManagerFactory emf;

    public UserFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UserFacade() {
        this(Persistence.createEntityManagerFactory("AngSeedServerPU"));
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createUserInDataBase(User user) {

        //Test Users
//        User user1 = new User("user", "testUser");
//        user1.AddRole("User");
//        users.put("user", user);
//
//        User admin = new User("admin", "testAdmin");
//        admin.AddRole("Admin");
//        users.put("admin", admin);
//
//        User both = new User("user_admin", "testBoth");
//        both.AddRole("User");
//        both.AddRole("Admin");
//        users.put("user_admin", both);

        
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(user);
//            em.persist(admin);
//            em.persist(both);

            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    public User getUserByUserId(String userName) {
        EntityManager em = getEntityManager();
        User us = (User)em.createQuery("SELECT u FROM User u WHERE u.userName = :myUserName").setParameter("myUserName", userName).getSingleResult();
        return us;
    }
    
    public List<User> getAllUsers(){
        EntityManager em = getEntityManager();
        return (List<User>) em.createQuery("SELECT p FROM User p").getResultList();
    }
    
    public boolean deleteUser(String username){
        EntityManager em = getEntityManager();
        if(userExistCheck(username)){
            try {
                em.getTransaction().begin();
                User us = em.find(User.class, username);
                em.remove(us);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return true;
        }
        return false;
    }
  

    public List<String> authenticateUser(String userName, String password) {
        User user = getUserByUserId(userName);
        return user != null && user.getPassword().equals(password) ? user.getRoles() : null;
    }

    public boolean userExistCheck(String username) {
        User checkUser = getUserByUserId(username);
        if (checkUser != null) {
            return true;
        }
        return false;
    }

}
