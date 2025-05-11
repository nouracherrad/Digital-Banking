package org.sdia.ebankingbackend.dtos;

import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int pageSize;
    private int totalPages;
private List<AccountOperationDTO> accountOperationDTOS;

}
