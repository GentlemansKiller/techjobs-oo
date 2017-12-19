package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view [GET HELP]
        Job aJob;
        aJob = JobData.findById(id);
        model.addAttribute("aJob",aJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a {DONE?}
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "new-job";
        }
        String name = jobForm.getName();

        int employerId = jobForm.getEmployerId();
        Employer employer = jobData.getEmployers().findById(employerId);

        int locationId = jobForm.getLocationId();
        Location location = jobData.getLocations().findById(locationId);

        int coreCompetencyId = jobForm.getCoreCompetencyId();
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(coreCompetencyId);

        int positionTypeId = jobForm.getPositionTypeId();
        PositionType positionType = jobData.getPositionTypes().findById(positionTypeId);

        Job newJob = new Job(name, employer, location, positionType, coreCompetency);
        JobData.add(newJob);

        int id = newJob.getId();
        String url = "redirect:?id=" + id;

        return url;

        //return "redirect:?id={newJob.id}";

    }
}
