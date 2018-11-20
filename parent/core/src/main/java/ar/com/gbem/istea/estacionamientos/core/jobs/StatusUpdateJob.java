package ar.com.gbem.istea.estacionamientos.core.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import ar.com.gbem.istea.estacionamientos.core.services.ReservationsService;

@Component
public class StatusUpdateJob extends QuartzJobBean {

	private ReservationsService reservationsService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (reservationsService == null) {
			reservationsService = (ReservationsService) context.getJobDetail().getJobDataMap()
					.get("reservationsService");
		}
		reservationsService.updateReservationsStatus();
	}

}
