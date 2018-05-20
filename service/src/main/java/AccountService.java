import exceptions.DaoException;

import java.util.Collections;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        try {
            accountDAO = new AccountDAO();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    //Constructor for tests
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public boolean create(String username, String password, String firstName, String lastName, String middleName,
                          String birthday, String phonePers, String phoneWork, String addressPers, String addressWork,
                          String email, int icq, String skype, String extra) {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            boolean result = accountDAO.create(username, password, firstName, lastName, middleName, birthday, phonePers,
                    phoneWork, addressPers, addressWork, email, icq, skype, extra);
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateInfo(String username, String firstName, String lastName, String middleName,
                              String birthday, String phonePers, String phoneWork, String addressPers, String addressWork,
                              String email, int icq, String skype, String extra) {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            int id = accountDAO.getId(username);
            Account account = new Account();
            account.setId(id);
            account.setUsername(username);
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setMiddleName(middleName);
            account.setBirthday(birthday);
            account.setPhonePers(phonePers);
            account.setPhoneWork(phoneWork);
            account.setAddressPers(addressPers);
            account.setAddressWork(addressWork);
            account.setEmail(email);
            account.setIcq(icq);
            account.setSkype(skype);
            account.setExtra(extra);
            boolean result = accountDAO.update(account);
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword) {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            int id = accountDAO.getId(username);
            boolean result = accountDAO.updatePassword(id, newPassword);
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            boolean result = accountDAO.remove(id);
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(String username) {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            int id = accountDAO.getId(username);
            boolean result = accountDAO.remove(id);
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account get(int id) {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            Account result = accountDAO.get(id);
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> getAll() {
        try {
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            List<Account> result = accountDAO.getAll();
            accountDAO.close();
            accountDAO = null;
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}