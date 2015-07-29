package org.mifosplatform.portfolio.loanaccount.service;


import java.util.Collection;

import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.ToApiJsonSerializer;
import org.mifosplatform.infrastructure.core.service.ThreadLocalContextUtil;
import org.mifosplatform.infrastructure.hooks.data.HookData;
import org.mifosplatform.infrastructure.hooks.event.HookEvent;
import org.mifosplatform.infrastructure.hooks.event.HookEventSource;
import org.mifosplatform.infrastructure.hooks.service.HookReadPlatformServiceImpl;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service

public class LoanRepaymentRemainderShedulerJob {
	private final PlatformSecurityContext context;
	private final ToApiJsonSerializer<CommandProcessingResult> toApiResultJsonSerializer;
	private final ApplicationContext applicationContext;
	private final HookReadPlatformServiceImpl hookReadPlatformServiceImpl;
	


	@Autowired
	public LoanRepaymentRemainderShedulerJob(
			PlatformSecurityContext context,
			ToApiJsonSerializer<CommandProcessingResult> toApiResultJsonSerializer,
			ApplicationContext applicationContext,HookReadPlatformServiceImpl hookReadPlatformServiceImpl) {
		this.context = context;
		this.toApiResultJsonSerializer = toApiResultJsonSerializer;
		this.applicationContext = applicationContext;
		this.hookReadPlatformServiceImpl=hookReadPlatformServiceImpl;
	}




	public void LoanRepaymentSmsReminder() {
		
		        final String authToken = ThreadLocalContextUtil.getAuthToken();
		        final String tenantIdentifier = ThreadLocalContextUtil.getTenant().getTenantIdentifier();
		        final AppUser appUser = this.context.authenticatedUser();		        
		        final HookEventSource hookEventSource = new HookEventSource("LoanRepaymentSmsReminder", "Remindclient");

		        final String serializedResult = this.toApiResultJsonSerializer.serialize("{reportName:Loan Repayment Reminders}");

		        final HookEvent applicationEvent = new HookEvent(hookEventSource, serializedResult, tenantIdentifier,appUser,authToken);
		        
		        applicationContext.publishEvent(applicationEvent);
		    }
}
