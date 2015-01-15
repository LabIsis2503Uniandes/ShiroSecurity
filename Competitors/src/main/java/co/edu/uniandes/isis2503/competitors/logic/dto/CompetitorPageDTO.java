/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.competitors.logic.dto;

import java.util.List;

/**
 *
 * @author Jj.alarcon10
 */
public class CompetitorPageDTO {
    
    
    private Long totalRecords;
    private List<CompetitorDTO> competitors;

    public CompetitorPageDTO(){
        
    }
    
    
    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<CompetitorDTO> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<CompetitorDTO> competitors) {
        this.competitors = competitors;
    }
    
    
    
    
}
