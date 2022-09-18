import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonPropertyOrder({"demand_no", "action_form", "resource_no", "customer_resource_no", "approval_rating", "approval_at"})
public class NrsDemand implements Serializable {

    @JsonProperty("demand_no")
    private String demandNo;

    @JsonProperty("action_form")
    private String actionForm;

    @JsonProperty("resource_no")
    private String resourceNo;

    @JsonProperty("customer_resource_no")
    private String customerResourceNo;

    @JsonProperty("approval_rating")
    private String approvalRating;

    @JsonProperty("approval_at")
    private String approvalAt;

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getActionForm() {
        return actionForm;
    }

    public void setActionForm(String actionForm) {
        this.actionForm = actionForm;
    }

    public String getResourceNo() {
        return resourceNo;
    }

    public void setResourceNo(String resourceNo) {
        this.resourceNo = resourceNo;
    }

    public String getCustomerResourceNo() {
        return customerResourceNo;
    }

    public void setCustomerResourceNo(String customerResourceNo) {
        this.customerResourceNo = customerResourceNo;
    }

    public String getApprovalRating() {
        return approvalRating;
    }

    public void setApprovalRating(String approvalRating) {
        this.approvalRating = approvalRating;
    }

    public String getApprovalAt() {
        return approvalAt;
    }

    public void setApprovalAt(String approvalAt) {
        this.approvalAt = approvalAt;
    }
}
