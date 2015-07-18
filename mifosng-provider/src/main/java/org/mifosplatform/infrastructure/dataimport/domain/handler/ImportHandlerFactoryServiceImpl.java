/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.dataimport.domain.handler.center.CenterDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.client.ClientDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.code.CodeDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.code.CodeValueDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.group.GroupDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.loan.LoanDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.loan.LoanRepaymentDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.office.OfficeDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.savings.SavingsDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.savings.SavingsTransactionDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.staff.StaffDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.user.UserDataImportHandler;
import org.mifosplatform.portfolio.client.service.ClientWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportHandlerFactoryServiceImpl implements ImportHandlerFactoryService {

    private final ClientWritePlatformService clientWritePlatformService;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    @Autowired
    public ImportHandlerFactoryServiceImpl(final ClientWritePlatformService clientWritePlatformService,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.clientWritePlatformService = clientWritePlatformService;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }

    @Override
    public DataImportHandler createImportHandler(Workbook workbook) throws IOException {

        if (workbook.getSheetIndex("Clients") == 0)
            return new ClientDataImportHandler(workbook, clientWritePlatformService, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("Groups") == 0)
            return new GroupDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("Centers") == 0)
            return new CenterDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("Loans") == 0)
            return new LoanDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("LoanRepayment") == 0)
            return new LoanRepaymentDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("Savings") == 0)
            return new SavingsDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("SavingsTransaction") == 0)
            return new SavingsTransactionDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("OfficesForImport") == 0)
            return new OfficeDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("Codes") == 0)
            return new CodeDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("CodeValues") == 0)
            return new CodeValueDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("StaffForImport") == 0)
            return new StaffDataImportHandler(workbook, commandsSourceWritePlatformService);
        else if (workbook.getSheetIndex("UsersForImport") == 0)
            return new UserDataImportHandler(workbook, commandsSourceWritePlatformService);

        throw new IllegalArgumentException("No work sheet found for processing : active sheet " + workbook.getSheetName(0));
    }

}
