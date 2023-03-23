package gov.kcg.kgo.service;

import gov.kcg.kgo.geoviewmodel.backend.rs.GeoTemplateOrganViewActionRs;
import gov.kcg.kgo.viewModel.backend.template.add.rq.TemplateAddActionRq;
import gov.kcg.kgo.viewModel.backend.template.add.rs.TemplateAddActionRs;
import gov.kcg.kgo.viewModel.backend.template.delete.rq.TemplateDeleteActionRq;
import gov.kcg.kgo.viewModel.backend.template.delete.rs.TemplateDeleteActionRs;
import gov.kcg.kgo.viewModel.backend.template.query.rq.TemplateQueryActionRq;
import gov.kcg.kgo.viewModel.backend.template.query.rs.TemplateQueryActionRs;
import gov.kcg.kgo.viewModel.backend.template.update.rq.TemplateUpdateActionRq;
import gov.kcg.kgo.viewModel.backend.template.update.rs.TemplateUpdateActionRs;
import gov.kcg.kgo.viewModel.backend.template.view.rq.TemplateViewActionRq;
import gov.kcg.kgo.viewModel.backend.template.view.rs.TemplateViewActionRs;

public interface TemplateService {

    TemplateDeleteActionRs templateDeleteAction(TemplateDeleteActionRq rq);

    TemplateAddActionRs templateAddAction(TemplateAddActionRq rq);

    TemplateUpdateActionRs templateUpdateAction(TemplateUpdateActionRq rq);

    TemplateQueryActionRs templateQueryAction(TemplateQueryActionRq rq);

    TemplateViewActionRs templateViewAction(TemplateViewActionRq rq);

    /** GEO 20211108 add for 機關審核表單 */
    GeoTemplateOrganViewActionRs templateOrganViewAction();
}
