package cs544.exercise16_1.bank.dao;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cs544.exercise16_1.bank.domain.Account;

public class AccountDAO implements IAccountDAO {
    Collection<Account> accountlist = new ArrayList<Account>();
    private SessionFactory sf = HibernateUtil.getSessionFactory();

    public void saveAccount(Account account) {
        // System.out.println("AccountDAO: saving account with accountnr
        // ="+account.getAccountnumber());
//        accountlist.add(account); // add the new

        sf.getCurrentSession().saveOrUpdate(account);
    }

    public void updateAccount(Account account) {
        // System.out.println("AccountDAO: update account with accountnr
        // ="+account.getAccountnumber());
//        Account accountexist = loadAccount(account.getAccountnumber());
//        if (accountexist != null) {
//            accountlist.remove(accountexist); // remove the old
//            accountlist.add(account); // add the new
//        }

        sf.getCurrentSession().saveOrUpdate(account);
    }

    public Account loadAccount(long accountnumber) {
        // System.out.println("AccountDAO: loading account with accountnr
        // ="+accountnumber);
//        for (Account account : accountlist) {
//            if (account.getAccountnumber() == accountnumber) {
//                return account;
//            }
//        }
//        return null;

        Account account = null;
        account = (Account) sf.getCurrentSession().get(Account.class, accountnumber);
        return account;
    }

    public Collection<Account> getAccounts() {
//        return accountlist;

        Collection<Account> accountlist = null;
        accountlist = sf.getCurrentSession().createQuery("from Account a join fetch a.entryList").list();
        return accountlist;
    }

}
