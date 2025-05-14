package oprag.project.gestionControleDAcces.services.impl;

import oprag.project.gestionControleDAcces.dto.BadgeDAO;
import oprag.project.gestionControleDAcces.exception.EntityNotFoundException;
import oprag.project.gestionControleDAcces.exception.ErrorCodes;
import oprag.project.gestionControleDAcces.repository.BadgeRepository;
import oprag.project.gestionControleDAcces.services.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeServiceImpl(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Override
    public BadgeDAO findByNumero(String numero) {
        return this.badgeRepository.findBadgeByNumero(numero)
                .map(BadgeDAO::fromEntity)
                .orElseThrow(()->new EntityNotFoundException("Aucun badge n'est trouvé pour ce numero", ErrorCodes.BADGE_NOT_FOUND));
    }

    @Override
    public List<BadgeDAO> findAll() {
        return this.badgeRepository.findAll().stream()
                .map(BadgeDAO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        assert id != null;
        if(this.badgeRepository.existsById(id)){
            this.badgeRepository.findById(id).ifPresent(badge -> {
                badge.setActive(false);
                this.badgeRepository.save(badge);
            });
        }
    }
}
