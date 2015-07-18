/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataimport.domain.populator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mifosplatform.infrastructure.dataimport.data.client.CompactClient;
import org.mifosplatform.infrastructure.dataimport.domain.handler.Result;
import org.mifosplatform.infrastructure.core.service.Page;
import org.mifosplatform.organisation.office.data.OfficeData;
import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.mifosplatform.portfolio.client.data.ClientData;
import org.mifosplatform.portfolio.client.service.ClientReadPlatformService;
import org.mifosplatform.infrastructure.core.service.SearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientSheetPopulator extends AbstractWorkbookPopulator {

    private static final Logger logger = LoggerFactory.getLogger(ClientSheetPopulator.class);

    private final ClientReadPlatformService clientReadPlatformService;
    private final OfficeReadPlatformService officeReadPlatformService;

    private List<CompactClient> clients;
    private ArrayList<String> officeNames;

    private Map<String, ArrayList<String>> officeToClients;
    private Map<Integer, Integer[]> officeNameToBeginEndIndexesOfClients;
    private Map<String, Integer> clientNameToClientId;

    private static final int OFFICE_NAME_COL = 0;
    private static final int CLIENT_NAME_COL = 1;
    private static final int CLIENT_ID_COL = 2;

    public ClientSheetPopulator(final ClientReadPlatformService clientReadPlatformService,
            final OfficeReadPlatformService officeReadPlatformService) {

        this.clientReadPlatformService = clientReadPlatformService;
        this.officeReadPlatformService = officeReadPlatformService;
    }

    @Override
    public Result downloadAndParse() {

        Result result = new Result();
        try {

            final SearchParameters searchParameters = SearchParameters.forClients(null, null, null, null, null, null, null, null, -1, null,
                    null, false);

            final Page<ClientData> clientDataPage = this.clientReadPlatformService.retrieveAll(searchParameters);
            final List<ClientData> clientDataCollection = clientDataPage.getPageItems();

            clients = new ArrayList<>();
            clientNameToClientId = new HashMap<>();

            for (ClientData aClientData : clientDataCollection) {

                CompactClient client = new CompactClient(aClientData);
                if (aClientData.isActive()) {
                    clients.add(client);
                }

                clientNameToClientId.put(client.getDisplayName().trim() + "(" + client.getId() + ")", client.getId());
            }

            Collection<OfficeData> officesCollection = this.officeReadPlatformService.retrieveAllOffices(false, null);

            officeNames = new ArrayList<>();
            for (OfficeData aOfficeData : officesCollection) {

                officeNames.add(aOfficeData.name());
            }

        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result populate(Workbook workbook) {

        Result result = new Result();
        Sheet clientSheet = workbook.createSheet("Clients");
        setLayout(clientSheet);

        try {
            setOfficeToClientsMap();
            populateClientsByOfficeName(clientSheet);
            clientSheet.protectSheet("");
        } catch (Exception e) {
            result.addError(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    private void populateClientsByOfficeName(Sheet clientSheet) {

        int rowIndex = 1, startIndex = 1, officeIndex = 0;
        officeNameToBeginEndIndexesOfClients = new HashMap<>();
        Row row = clientSheet.createRow(rowIndex);

        for (String officeName : officeNames) {
            startIndex = rowIndex + 1;
            writeString(OFFICE_NAME_COL, row, officeName);
            ArrayList<String> clientList = new ArrayList<>();

            if (officeToClients.containsKey(officeName)) clientList = officeToClients.get(officeName);

            if (!clientList.isEmpty()) {
                for (String clientName : clientList) {
                    writeString(CLIENT_NAME_COL, row, clientName);
                    writeInt(CLIENT_ID_COL, row, clientNameToClientId.get(clientName));
                    row = clientSheet.createRow(++rowIndex);
                }
                officeNameToBeginEndIndexesOfClients.put(officeIndex++, new Integer[] { startIndex, rowIndex });
            } else
                officeIndex++;
        }
    }

    private void setOfficeToClientsMap() {

        officeToClients = new HashMap<>();
        for (CompactClient person : clients)
            add(person.getOfficeName().trim().replaceAll("[ )(]", "_"), person.getDisplayName().trim() + "(" + person.getId() + ")");
    }

    private void add(String key, String value) {

        ArrayList<String> values = officeToClients.get(key);
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        officeToClients.put(key, values);
    }

    private void setLayout(Sheet worksheet) {

        Row rowHeader = worksheet.createRow(0);
        rowHeader.setHeight((short) 500);
        worksheet.setColumnWidth(OFFICE_NAME_COL, 6000);

        for (int colIndex = 1; colIndex <= 10; colIndex++)
            worksheet.setColumnWidth(colIndex, 6000);

        writeString(OFFICE_NAME_COL, rowHeader, "Office Names");
        writeString(CLIENT_NAME_COL, rowHeader, "Client Names");
        writeString(CLIENT_ID_COL, rowHeader, "Client ID");
    }

    public List<CompactClient> getClients() {
        return clients;
    }

    public String[] getOfficeNames() {
        return officeNames.toArray(new String[officeNames.size()]);
    }

    public Integer getClientsSize() {
        return clients.size();
    }

    public Map<Integer, Integer[]> getOfficeNameToBeginEndIndexesOfClients() {
        return officeNameToBeginEndIndexesOfClients;
    }

    public Map<String, ArrayList<String>> getOfficeToClients() {
        return officeToClients;
    }

    public Map<String, Integer> getClientNameToClientId() {
        return clientNameToClientId;
    }
}
