package com.lld.bookmyshow.services;

import com.lld.bookmyshow.models.Show;
import com.lld.bookmyshow.models.ShowSeatType;
import com.lld.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculator {

    private final ShowSeatTypeRepository showSeatTypeRepository;

    public PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public double calculatePrice(Show show, List<Long> showSeatIds) {
        return showSeatTypeRepository.findAllByShow((show)).stream()
                .filter(showSeatType -> showSeatIds.contains(showSeatType.getId()))
                .mapToDouble(ShowSeatType::getPrice)
                .sum();
    }
}
