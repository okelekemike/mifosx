/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator;

import java.io.IOException;

import org.mifosplatform.infrastructure.dataimport.domain.populator.center.CenterWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.client.ClientWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.code.CodeValueWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.code.CodeWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.group.GroupWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.loan.LoanProductSheetPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.loan.LoanRepaymentWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.loan.LoanWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.office.OfficeWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.savings.SavingsProductSheetPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.savings.SavingsTransactionWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.savings.SavingsWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.staff.StaffWorkbookPopulator;
import org.mifosplatform.infrastructure.dataimport.domain.populator.user.UserWorkbookPopulator;
import org.mifosplatform.infrastructure.codes.service.CodeReadPlatformService;
import org.mifosplatform.infrastructure.codes.service.CodeValueReadPlatformService;

import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.mifosplatform.organisation.staff.service.StaffReadPlatformService;

import org.mifosplatform.portfolio.client.service.ClientReadPlatformService;
import org.mifosplatform.portfolio.fund.service.FundReadPlatformService;
import org.mifosplatform.portfolio.group.service.GroupReadPlatformService;
import org.mifosplatform.portfolio.loanaccount.service.LoanReadPlatformService;
import org.mifosplatform.portfolio.loanproduct.service.LoanProductReadPlatformService;
import org.mifosplatform.portfolio.savings.service.SavingsAccountReadPlatformService;
import org.mifosplatform.portfolio.savings.service.SavingsProductReadPlatformService;
import org.mifosplatform.useradministration.service.RoleReadPlatformService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.identitymanagement.model.User;

@Service
public class WorkbookPopulatorFactoryServiceImpl implements WorkbookPopulatorFactoryService {

    private final OfficeReadPlatformService officeReadPlatformService;
    private final StaffReadPlatformService staffReadPlatformService;
    private final ClientReadPlatformService clientReadPlatformService;
    private final GroupReadPlatformService groupReadPlatformService;
    private final LoanProductReadPlatformService loanProductReadPlatformService;
    private final SavingsProductReadPlatformService savingsProductReadPlatformService;
    private final FundReadPlatformService fundReadPlatformService;
    private final CodeValueReadPlatformService codeValueReadPlatformService;
    private final LoanReadPlatformService loanReadPlatformService;
    private final SavingsAccountReadPlatformService savingsAccountReadPlatformService;
    private final CodeReadPlatformService codeReadPlatformService;
    private final RoleReadPlatformService roleReadPlatformService;

    @Autowired
    public WorkbookPopulatorFactoryServiceImpl(final OfficeReadPlatformService officeReadPlatformService,
            final StaffReadPlatformService staffReadPlatformService, final ClientReadPlatformService clientReadPlatformService,
            final GroupReadPlatformService groupReadPlatformService, final LoanProductReadPlatformService loanProductReadPlatformService,
            final SavingsProductReadPlatformService savingsProductReadPlatformService,
            final FundReadPlatformService fundReadPlatformService, final CodeValueReadPlatformService codeValueReadPlatformService,
            final LoanReadPlatformService loanReadPlatformService,
            final SavingsAccountReadPlatformService savingsAccountReadPlatformService,
            final CodeReadPlatformService codeReadPlatformService, final RoleReadPlatformService roleReadPlatformService) {

        this.officeReadPlatformService = officeReadPlatformService;
        this.staffReadPlatformService = staffReadPlatformService;
        this.clientReadPlatformService = clientReadPlatformService;
        this.groupReadPlatformService = groupReadPlatformService;
        this.loanProductReadPlatformService = loanProductReadPlatformService;
        this.savingsProductReadPlatformService = savingsProductReadPlatformService;
        this.fundReadPlatformService = fundReadPlatformService;
        this.codeValueReadPlatformService = codeValueReadPlatformService;
        this.loanReadPlatformService = loanReadPlatformService;
        this.savingsAccountReadPlatformService = savingsAccountReadPlatformService;
        this.codeReadPlatformService = codeReadPlatformService;
        this.roleReadPlatformService = roleReadPlatformService;
    }

    @Override
    public WorkbookPopulator createWorkbookPopulator(String parameter, String template) throws IOException {

        if (template.trim().equals("client"))
            return new ClientWorkbookPopulator(parameter, new OfficeSheetPopulator(officeReadPlatformService), new PersonnelSheetPopulator(
                    Boolean.FALSE, officeReadPlatformService, staffReadPlatformService), new CodeValueSheetPopulator("ClientType",
                    codeValueReadPlatformService), new CodeValueSheetPopulator("ClientClassification", codeValueReadPlatformService),
                    new GroupSheetPopulator(officeReadPlatformService, groupReadPlatformService));
        else if (template.trim().equals("groups"))
            return new GroupWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService), new PersonnelSheetPopulator(
                    Boolean.FALSE, officeReadPlatformService, staffReadPlatformService), new ClientSheetPopulator(
                    clientReadPlatformService, officeReadPlatformService));
        else if (template.trim().equals("center"))
            return new CenterWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService), new PersonnelSheetPopulator(
                    Boolean.FALSE, officeReadPlatformService, staffReadPlatformService));
        else if (template.trim().equals("loan"))
            return new LoanWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService), new ClientSheetPopulator(
                    clientReadPlatformService, officeReadPlatformService), new GroupSheetPopulator(officeReadPlatformService,
                    groupReadPlatformService), new PersonnelSheetPopulator(Boolean.TRUE, officeReadPlatformService,
                    staffReadPlatformService), new LoanProductSheetPopulator(loanProductReadPlatformService), new ExtrasSheetPopulator(
                    fundReadPlatformService, codeValueReadPlatformService));
        else if (template.trim().equals("loanRepaymentHistory"))
            return new LoanRepaymentWorkbookPopulator(loanReadPlatformService, new OfficeSheetPopulator(officeReadPlatformService),
                    new ClientSheetPopulator(clientReadPlatformService, officeReadPlatformService), new ExtrasSheetPopulator(
                            fundReadPlatformService, codeValueReadPlatformService));
        else if (template.trim().equals("savings"))
            return new SavingsWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService), new ClientSheetPopulator(
                    clientReadPlatformService, officeReadPlatformService), new GroupSheetPopulator(officeReadPlatformService,
                    groupReadPlatformService), new PersonnelSheetPopulator(Boolean.TRUE, officeReadPlatformService,
                    staffReadPlatformService), new SavingsProductSheetPopulator(savingsProductReadPlatformService));
        else if (template.trim().equals("savingsTransactionHistory"))
            return new SavingsTransactionWorkbookPopulator(savingsAccountReadPlatformService, new OfficeSheetPopulator(
                    officeReadPlatformService), new ClientSheetPopulator(clientReadPlatformService, officeReadPlatformService),
                    new ExtrasSheetPopulator(fundReadPlatformService, codeValueReadPlatformService));
        else if (template.trim().equals("office"))
            return new OfficeWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService));
        else if (template.trim().equals("codes"))
            return new CodeWorkbookPopulator();
        else if (template.trim().equals("codevalues"))
            return new CodeValueWorkbookPopulator(new CodeSheetPopulator(codeReadPlatformService));
        else if (template.trim().equals("staff"))
            return new StaffWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService));
        else if (template.trim().equals("users"))
            return new UserWorkbookPopulator(new OfficeSheetPopulator(officeReadPlatformService), new PersonnelSheetPopulator(Boolean.TRUE,
                    officeReadPlatformService, staffReadPlatformService), new UserRoleSheetPopulator(roleReadPlatformService));

        throw new IllegalArgumentException("Can't find populator.");
    }
}
