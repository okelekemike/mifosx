/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.handler.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.dataimport.data.client.Client;
import org.mifosplatform.infrastructure.dataimport.domain.handler.AbstractDataImportHandler;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.dataimport.services.utils.StringUtils;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.portfolio.client.service.ClientWritePlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ClientDataImportHandler extends AbstractDataImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientDataImportHandler.class);

    private static final int OFFICE_NAME_COL = 0;
    private static final int STAFF_NAME_COL = 1;
    private static final int FIRST_NAME_COL = 2;
    private static final int MIDDLE_NAME_COL = 3;
    private static final int LAST_NAME_COL = 4;
    private static final int MOBILE_NO_COL = 5;
    private static final int DATE_OF_BIRTH_COL = 6;
    private static final int GENDER_COL = 7;
    private static final int CLIENT_TYPE_COL = 8;
    private static final int CLIENT_CLASSIFICATION_COL = 9;
    private static final int EXTERNAL_ID_COL = 10;
    private static final int ACTIVE_COL = 11;
    private static final int ACTIVATION_DATE_COL = 12;
    private static final int GROUP_NAME_COL = 13;
    private static final int STATUS_COL = 14;
    @SuppressWarnings("unused")
    private static final int WARNING_COL = 15;
    @SuppressWarnings("unused")
    private static final int RELATIONAL_OFFICE_NAME_COL = 18;
    @SuppressWarnings("unused")
    private static final int RELATIONAL_OFFICE_OPENING_DATE_COL = 20;

    private List<Client> clients;
    private String clientType;

    private final Workbook workbook;
    @SuppressWarnings("unused")
    private final ClientWritePlatformService clientWritePlatformService;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    public ClientDataImportHandler(Workbook workbook, final ClientWritePlatformService clientWritePlatformService,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {

        this.workbook = workbook;

        this.clientWritePlatformService = clientWritePlatformService;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
        clients = new ArrayList<>();
    }

    @Override
    public Result parse() {

        Result result = new Result();

        Sheet clientSheet = workbook.getSheet("Clients");
        Integer noOfEntries = getNumberOfRows(clientSheet, 0);
        clientType = getClientType();

        for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
            Row row;
            try {
                row = clientSheet.getRow(rowIndex);
                if (isNotImported(row, STATUS_COL)) {
                    clients.add(parseAsClient(row));
                }
            } catch (Exception e) {
                logger.error("row = " + rowIndex, e);
                result.addError("Row = " + rowIndex + " , " + e.getMessage());
            }
        }

        return result;
    }

    private String getClientType() {

        String clientType = null;

        if (workbook.getSheet("Groups") == null)
            clientType = "Individual";
        else
            clientType = "Corporate";

        return clientType;
    }

    private Client parseAsClient(Row row) {

        Client client = null;
        String buffer;

        String officeName = readAsString(OFFICE_NAME_COL, row);
        Long officeId = officeName.equals("") ? null : getIdByName(workbook.getSheet("Offices"), officeName).longValue();

        String staffName = readAsString(STAFF_NAME_COL, row);
        Long staffId = staffName.equals("") ? null : getIdByName(workbook.getSheet("Staff"), staffName).longValue();

        buffer = readAsString(FIRST_NAME_COL, row);
        String firstName = buffer.equals("") ? null : buffer;

        buffer = readAsString(MIDDLE_NAME_COL, row);
        String middleName = buffer.equals("") ? null : buffer;

        buffer = readAsString(LAST_NAME_COL, row);
        String lastName = buffer.equals("") ? null : buffer;

        buffer = readAsString(MOBILE_NO_COL, row);
        String mobileNo = buffer.equals("") ? null : buffer;

        buffer = readAsDate(DATE_OF_BIRTH_COL, row);
        String dateOfBirth = buffer.equals("") ? null : buffer;

        buffer = getGenderId(readAsString(GENDER_COL, row));
        String gender = buffer.equals("") ? null : buffer;

        String clientTypeName = readAsString(CLIENT_TYPE_COL, row);
        Integer clientTypeIdInt = getIdByName(workbook.getSheet("ClientType"), clientTypeName);
        String clientTypeId = clientTypeIdInt != 0 ? clientTypeIdInt.toString() : null;

        String clientClassificationName = readAsString(CLIENT_CLASSIFICATION_COL, row);
        Integer clientClassificationIdInt = getIdByName(workbook.getSheet("ClientClassification"), clientClassificationName);
        String clientClassificationId = clientClassificationIdInt != 0 ? clientClassificationIdInt.toString() : null;

        buffer = readAsString(EXTERNAL_ID_COL, row);
        String externalId = buffer.equals("") ? null : buffer;

        buffer = readAsBoolean(ACTIVE_COL, row).toString();
        String active = buffer.equals("") ? null : buffer;

        buffer = readAsDate(ACTIVATION_DATE_COL, row);
        String activationDate = buffer.equals("") ? null : buffer;

        if (StringUtils.isBlank(firstName)) { throw new IllegalArgumentException("Name is blank"); }

        client = new Client(firstName, lastName, middleName, mobileNo, gender, dateOfBirth, clientTypeId, clientClassificationId,
                activationDate, active, externalId, officeId, staffId, row.getRowNum());

        if (!clientType.equals("Individual")) {

            String groupName = readAsString(GROUP_NAME_COL, row);
            Integer groupId = getIdByName(workbook.getSheet("Groups"), groupName);
            groupId = groupId != 0 ? groupId : null;
            client.setGruopID(groupId);
        }

        return client;
    }

    @Override
    public Result upload() {

        Result result = new Result();
        Sheet clientSheet = workbook.getSheet("Clients");

        for (Client client : clients) {
            try {
                if (client.isValidated()) {

                    Gson gson = new Gson();
                    String payload = gson.toJson(client);
                    logger.info(payload);

                    final CommandWrapper commandRequest = new CommandWrapperBuilder().createClient().withJson(payload).build();

                    final CommandProcessingResult commandProcessingResult = this.commandsSourceWritePlatformService
                            .logCommandSource(commandRequest);

                    if (!clientType.equals("Individual")) {

                        Integer groupId = client.getGroupId();

                        if (groupId != 0) {

                            Long clientId = commandProcessingResult.getClientId().longValue();
                            String[] clientIdAsArray = { clientId.toString() };
                            String payloadJson = gson.toJson(clientIdAsArray);
                            payloadJson = "{\"clientMembers\":" + payloadJson + "}";

                            final CommandWrapper commandRequestForGroupAssociation = new CommandWrapperBuilder().withJson(payloadJson)
                                    .associateClientsToGroup(groupId.longValue()).build();
                            @SuppressWarnings("unused")
                            final CommandProcessingResult commandProcessingResultForGroupAssociation = this.commandsSourceWritePlatformService
                                    .logCommandSource(commandRequestForGroupAssociation);
                        }
                    }

                    Cell statusCell = clientSheet.getRow(client.getRowIndex()).createCell(STATUS_COL);
                    statusCell.setCellValue("Imported");
                    statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.LIGHT_GREEN));
                } else {
                    throw new RuntimeException(client.getValidateStatus());
                }

            } catch (RuntimeException e) {

                String message;
                if (e.getMessage() != null) {
                    message = parseStatus(e.getMessage());
                } else if (e.getLocalizedMessage() != null) {
                    message = e.getLocalizedMessage();
                } else {
                    message = "";
                }

                logger.error(message);

                Cell statusCell = clientSheet.getRow(client.getRowIndex()).createCell(STATUS_COL);
                statusCell.setCellValue(message);
                statusCell.setCellStyle(getCellStyle(workbook, IndexedColors.RED));
                result.addError("Row = " + client.getRowIndex() + " ," + message);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        clientSheet.setColumnWidth(STATUS_COL, 15000);
        writeString(STATUS_COL, clientSheet.getRow(0), "Status");
        return result;
    }

    public List<Client> getClients() {
        return clients;
    }

    private String getGenderId(String type) {
        String id = "";
        type = type.trim();
        switch (type) {
            case "Male":
                id = "22";
            break;
            case "Female":
                id = "24";
            break;

        }
        return id;
    }
}
