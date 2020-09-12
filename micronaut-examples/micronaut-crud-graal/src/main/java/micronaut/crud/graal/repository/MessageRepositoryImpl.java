package micronaut.crud.graal.repository;

import io.micronaut.spring.tx.annotation.Transactional;
import micronaut.crud.graal.domain.MessageDomain;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Singleton
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public MessageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public String uuid() {
        return entityManager.createNativeQuery("SELECT uuid()")
                .getSingleResult().toString();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageDomain> findById(@NotBlank String id) {
        return Optional.ofNullable(entityManager.find(MessageDomain.class, id));
    }

    @Override
    @Transactional
    public MessageDomain save(@NotBlank String id, @NotBlank String message) {
        MessageDomain domain = new MessageDomain(id, message);
        entityManager.persist(domain);
        entityManager.flush();
        return domain;
    }

    @Override
    @Transactional
    public void deleteById(@NotBlank String id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createNativeQuery("DELETE FROM message").executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDomain> findAll() {
        return entityManager.createQuery("SELECT m FROM MessageDomain AS m", MessageDomain.class)
                .getResultList();
    }

    @Override
    @Transactional
    public int update(@NotBlank String id, @NotBlank String message) {
        return entityManager.createQuery("UPDATE MessageDomain m SET message = :message where id = :id")
                .setParameter("message", message)
                .setParameter("id", id)
                .executeUpdate();
    }
}
