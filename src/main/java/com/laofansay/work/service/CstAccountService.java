package com.laofansay.work.service;

import com.laofansay.work.domain.CstAccount;
import com.laofansay.work.domain.Customer;
import com.laofansay.work.domain.enumeration.CstStatus;
import com.laofansay.work.repository.CstAccountRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.laofansay.work.domain.CstAccount}.
 */
@Service
public class CstAccountService {

    private static final Logger log = LoggerFactory.getLogger(CstAccountService.class);

    private final CstAccountRepository cstAccountRepository;

    public CstAccountService(CstAccountRepository cstAccountRepository) {
        this.cstAccountRepository = cstAccountRepository;
    }

    /**
     * Save a cstAccount.
     *
     * @param cstAccount the entity to save.
     * @return the persisted entity.
     */
    public CstAccount save(CstAccount cstAccount) {
        log.debug("Request to save CstAccount : {}", cstAccount);
        return cstAccountRepository.save(cstAccount);
    }

    /**
     * Update a cstAccount.
     *
     * @param cstAccount the entity to save.
     * @return the persisted entity.
     */
    public CstAccount update(CstAccount cstAccount) {
        log.debug("Request to update CstAccount : {}", cstAccount);
        return cstAccountRepository.save(cstAccount);
    }

    /**
     * Partially update a cstAccount.
     *
     * @param cstAccount the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CstAccount> partialUpdate(CstAccount cstAccount) {
        log.debug("Request to partially update CstAccount : {}", cstAccount);

        return cstAccountRepository
            .findById(cstAccount.getId())
            .map(existingCstAccount -> {
                if (cstAccount.getName() != null) {
                    existingCstAccount.setName(cstAccount.getName());
                }
                if (cstAccount.getProvider() != null) {
                    existingCstAccount.setProvider(cstAccount.getProvider());
                }
                if (cstAccount.getRbAccount() != null) {
                    existingCstAccount.setRbAccount(cstAccount.getRbAccount());
                }
                if (cstAccount.getRbPwd() != null) {
                    existingCstAccount.setRbPwd(cstAccount.getRbPwd());
                }
                if (cstAccount.getRbToken() != null) {
                    existingCstAccount.setRbToken(cstAccount.getRbToken());
                }
                if (cstAccount.getStatus() != null) {
                    existingCstAccount.setStatus(cstAccount.getStatus());
                }
                if (cstAccount.getTimesByDay() != null) {
                    existingCstAccount.setTimesByDay(cstAccount.getTimesByDay());
                }
                if (cstAccount.getCreatedDate() != null) {
                    existingCstAccount.setCreatedDate(cstAccount.getCreatedDate());
                }
                if (cstAccount.getChannel() != null) {
                    existingCstAccount.setChannel(cstAccount.getChannel());
                }

                return existingCstAccount;
            })
            .map(cstAccountRepository::save);
    }

    /**
     * Get all the cstAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CstAccount> findAll(Pageable pageable) {
        log.debug("Request to get all CstAccounts");
        return cstAccountRepository.findAll(pageable);
    }

    /**
     * Get one cstAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CstAccount> findOne(String id) {
        log.debug("Request to get CstAccount : {}", id);
        return cstAccountRepository.findById(id);
    }

    /**
     * Delete the cstAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CstAccount : {}", id);
        cstAccountRepository.deleteById(id);
    }

    public List<CstAccount> findByCstId(String id) {
        CstAccount account = new CstAccount();
        account.setStatus(CstStatus.ENABLED);
        account.setCustomer(new Customer().id(id));
        return cstAccountRepository.findAll(Example.of(account));
    }
}
