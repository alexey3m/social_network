package com.ershov.socialnet.service;

import com.ershov.socialnet.common.AccountInGroup;
import com.ershov.socialnet.common.Group;
import com.ershov.socialnet.common.enums.GroupRole;
import com.ershov.socialnet.common.enums.GroupStatus;
import com.ershov.socialnet.dao.exceptions.DaoNameException;
import com.ershov.socialnet.dao.repository.GroupRepository;
import com.ershov.socialnet.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ershov.socialnet.dao.repository.specifications.GroupSpecification.searchByMemberId;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class GroupService {
    private static final int SEARCH_RESULT_PER_PAGE = 5;

    private GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public boolean create(Group group) throws DaoNameException {
        String name = group.getName();
        if (groupRepository.existsByName(name)) {
            throw new DaoNameException("Name " + name + " already used");
        } else {
            groupRepository.saveAndFlush(group);
            return true;
        }
    }

    public Group get(int groupId) {
        return groupRepository.findById(groupId).orElseThrow(ServiceException::new);
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public List<Group> searchByString(String search, int page) {
        return groupRepository.findByNameIgnoreCaseContaining(search, createPageRequest(page)).getContent();
    }

    public long searchByStringCount(String search) {
        return groupRepository.countByNameIgnoreCaseContaining(search);
    }

    public List<Group> getAllByUserId(int userId) {
        return groupRepository.findAll(searchByMemberId(userId));
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            for (AccountInGroup accountInGroup : foundGroup.get().getAccounts()) {
                if (accountInGroup.getUserMemberId() == memberId) {
                    return accountInGroup.getRole();
                }
            }
        }
        return GroupRole.UNKNOWN;
    }

    public byte[] getPhoto(int groupId) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            byte[] photo = foundGroup.get().getPhoto();
            return photo.length == 0 ? null : photo;
        } else {
            return null;
        }
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            for (AccountInGroup accountInGroup : foundGroup.get().getAccounts()) {
                if (accountInGroup.getUserMemberId() == memberId) {
                    return accountInGroup.getStatus();
                }
            }
        }
        return GroupStatus.UNKNOWN;
    }

    @Transactional
    public boolean addPendingMemberToGroup(int groupId, int newMemberId) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            Group group = foundGroup.get();
            List<AccountInGroup> accounts = group.getAccounts();
            accounts.add(new AccountInGroup(newMemberId, GroupRole.USER, GroupStatus.PENDING));
            group.setAccounts(accounts);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean setStatusMemberInGroup(int groupId, int memberId, GroupStatus status) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            Group group = foundGroup.get();
            List<AccountInGroup> accounts = group.getAccounts();
            for (AccountInGroup accountInGroup : accounts) {
                if (accountInGroup.getUserMemberId() == memberId) {
                    accountInGroup.setStatus(status);
                    break;
                }
            }
            group.setAccounts(accounts);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean setRoleMemberInGroup(int groupId, int memberId, GroupRole role) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            Group group = foundGroup.get();
            List<AccountInGroup> accounts = group.getAccounts();
            for (AccountInGroup accountInGroup : accounts) {
                if (accountInGroup.getUserMemberId() == memberId) {
                    accountInGroup.setRole(role);
                    break;
                }
            }
            group.setAccounts(accounts);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeMemberFromGroup(int groupId, int memberToDeleteId) {
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        if (foundGroup.isPresent()) {
            Group group = foundGroup.get();
            List<AccountInGroup> accounts = group.getAccounts();
            for (AccountInGroup accountInGroup : accounts) {
                if (accountInGroup.getUserMemberId() == memberToDeleteId) {
                    accounts.remove(accountInGroup);
                    break;
                }
            }
            group.setAccounts(accounts);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean update(Group group) {
        groupRepository.save(group);
        return true;
    }

    @Transactional
    public boolean remove(int id) {
        groupRepository.deleteById(id);
        return true;
    }

    private Pageable createPageRequest(int page) {
        return of(page, SEARCH_RESULT_PER_PAGE);
    }

}