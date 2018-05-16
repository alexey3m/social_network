import exceptions.DaoException;

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
            return accountDAO.create(username, password, firstName, lastName, middleName, birthday, phonePers,
                    phoneWork, addressPers, addressWork, email, icq, skype, extra);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateInfo(String username, String firstName, String lastName, String middleName,
                              String birthday, String phonePers, String phoneWork, String addressPers, String addressWork,
                              String email, int icq, String skype, String extra) {
        try {
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
            return accountDAO.update(account);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword) {
        try {
            int id = accountDAO.getId(username);
            return accountDAO.updatePassword(id, newPassword);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        try {
            return accountDAO.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(String username) {
        try {
            int id = accountDAO.getId(username);
            return accountDAO.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account get(int id) {
        try {
            return accountDAO.get(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> getAll() {
        try {
            return accountDAO.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }
}