package ar.com.gbem.istea.estacionamientos.core;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.annotation.PreDestroy;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import ar.com.gbem.istea.estacionamientos.core.jobs.StatusUpdateJob;
import ar.com.gbem.istea.estacionamientos.core.services.ReservationsService;

@Configuration
public class QuartzConfiguration {

	private Scheduler scheduler;

	public QuartzConfiguration(ReservationsService reservationsService, ApplicationContext context)
			throws SchedulerException {

		scheduler = (Scheduler) context.getBean("quartzScheduler");

		JobDetail job = newJob(StatusUpdateJob.class).build();
		job.getJobDataMap().put("reservationsService", reservationsService);

		Trigger trigger = newTrigger().startNow()
				.withSchedule(simpleSchedule().withIntervalInMinutes(1).repeatForever()).build();

		scheduler.scheduleJob(job, trigger);
	}

	@PreDestroy
	protected void preDestroy() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			// nothing
		}
	}

}
