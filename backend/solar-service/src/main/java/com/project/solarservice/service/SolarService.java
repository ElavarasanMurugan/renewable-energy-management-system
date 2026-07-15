package com.project.solarservice.service;

import com.project.solarservice.dto.SolarRequestDto;
import com.project.solarservice.entity.Solar;
import com.project.solarservice.exception.ResourceNotFoundException;
import com.project.solarservice.repository.SolarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolarService {

    private final SolarRepository solarRepository;

    public SolarService(SolarRepository solarRepository) {
        this.solarRepository = solarRepository;
    }

    public Solar registerPanels(SolarRequestDto solarRequestDto){
        Solar solar = new Solar();
        solar.setId(solarRequestDto.getId());
        solar.setPanel_name(solarRequestDto.getPanel_name());
        solar.setLocation(solarRequestDto.getLocation());
        solar.setCapacity_kw(solarRequestDto.getCapacity_kw());
        solar.setStatus(solarRequestDto.getStatus());
        solar.setCurrent_generation(solarRequestDto.getCurrent_generation());

        return solarRepository.save(solar);
    }

    public List<Solar> getAllPanelDetails(){
        return solarRepository.findAll();
    }

    public Object getPanelById(Long id){
        return solarRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("The requested solar not found/exist"));
    }

    public Solar updatePanel(Long id,SolarRequestDto solarRequestDto){
        Solar solar = solarRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("The requested solar not found/exist"));
//        solar.setId(solarRequestDto.getId());
        solar.setPanel_name(solarRequestDto.getPanel_name());
        solar.setLocation(solarRequestDto.getLocation());
        solar.setCapacity_kw(solarRequestDto.getCapacity_kw());
        solar.setStatus(solarRequestDto.getStatus());
        solar.setCurrent_generation(solarRequestDto.getCurrent_generation());

        return solarRepository.save(solar);
    }

    public String deletePanel(Long id){
        solarRepository.deleteById(id);
        return "Solar panel with id " + id + " is deleted successfully";
    }
}
