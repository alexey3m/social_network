package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.dao.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.getjavajob.training.web1803.dao.repository.specifications.GroupSpecification.searchByMemberId;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class GroupService {
    private static final int SEARCH_RESULT_PER_PAGE = 5;

    private GroupRepository repository;

    @Autowired
    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public GroupService() {
    }

    public boolean create(Group group) throws DaoNameException {
        String name = group.getName();
        if (repository.existsByName(name)) {
            throw new DaoNameException("Name " + name + " already used");
        } else {
            repository.saveAndFlush(group);
            return true;
        }
    }

    public Group get(int groupId) {
        return repository.findById(groupId).get();
    }

    public List<Group> getAll() {
        return repository.findAll();
    }

    public List<Group> searchByString(String search, int page) {
        return repository.findByNameIgnoreCaseContaining(search, createPageRequest(page)).getContent();
    }

    public long searchByStringCount(String search) {
        return repository.countByNameIgnoreCaseContaining(search);
    }

    public List<Group> getAllByUserId(int userId) {
        return repository.findAll(searchByMemberId(userId));
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        Group group = repository.findById(groupId).get();
        for (AccountInGroup accountInGroup : group.getAccounts()) {
            if (accountInGroup.getUserMemberId() == memberId) {
                return accountInGroup.getRole();
            }
        }
        return GroupRole.UNKNOWN;
    }

    public byte[] getPhoto(int id) {
        byte[] photo = repository.findById(id).get().getPhoto();
        if (photo != null) {
            return photo.length == 0 ? null : photo;
        } else {
            return null;
        }
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        Group group = repository.findById(groupId).get();
        for (AccountInGroup accountInGroup : group.getAccounts()) {
            if (accountInGroup.getUserMemberId() == memberId) {
                return accountInGroup.getStatus();
            }
        }
        return GroupStatus.UNKNOWN;
    }

    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
        Group currentGroup = repository.findById(idGroup).get();
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        accounts.add(new AccountInGroup(idNewMember, GroupRole.USER, GroupStatus.PENDING));
        currentGroup.setAccounts(accounts);
        repository.save(currentGroup);
        return true;
    }

    public boolean setStatusMemberInGroup(int idGroup, int idMember, GroupStatus status) {
        Group currentGroup = repository.findById(idGroup).get();
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        for (AccountInGroup accountInGroup : accounts) {
            if (accountInGroup.getUserMemberId() == idMember) {
                accountInGroup.setStatus(status);
                break;
            }
        }
        currentGroup.setAccounts(accounts);
        repository.save(currentGroup);
        return true;
    }

    public boolean setRoleMemberInGroup(int idGroup, int idMember, GroupRole role) {
        Group currentGroup = repository.findById(idGroup).get();
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        for (AccountInGroup accountInGroup : accounts) {
            if (accountInGroup.getUserMemberId() == idMember) {
                accountInGroup.setRole(role);
                break;
            }
        }
        currentGroup.setAccounts(accounts);
        repository.save(currentGroup);
        return true;
    }

    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) {
        Group currentGroup = repository.findById(idGroup).get();
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        for (AccountInGroup accountInGroup : accounts) {
            if (accountInGroup.getUserMemberId() == idMemberToDelete) {
                accounts.remove(accountInGroup);
                break;
            }
        }
        currentGroup.setAccounts(accounts);
        repository.save(currentGroup);
        return true;
    }

    public int getId(String name) {
        return repository.findByName(name).getId();
    }

    public boolean update(Group group) {
        repository.save(group);
        return true;
    }

    public boolean remove(int id) {
        repository.deleteById(id);
        return true;
    }

    private Pageable createPageRequest(int page) {
        return of(page, SEARCH_RESULT_PER_PAGE);
    }

}