package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelSQL;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;

interface UserRepoCustom {
    List<UserDataModelSQL> searchUsers(Page page, SearchUsersQuery query);
}
