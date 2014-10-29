package mysword.action.knowledgesharing;

import com.opensymphony.xwork2.ActionSupport;
import mysword.bean.knowledgesharing.KnowledgeSharingBean;
import mysword.dao.knowledgesharing.KnowledgeSharingDAO;
import mysword.utils.ServletActionContextUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeSharingAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String share_Id = "";
    private String subject = "";
    private String content = "";
    private String poster = "All";
    private String approval = "All";
	private String startTime = "";
	private String endTime = "";
    private int pagenum = 1;
    private String pagesize = "50";
	private int totalCount = 0;
    private String filename = "mysword.eml";
    private InputStream fileStream;
    private boolean web = true;
    private String[] posterArray = new String[]{"All"};
	private ArrayList<KnowledgeSharingBean> knowledgeList = new ArrayList<KnowledgeSharingBean>();

    public String getShare_Id() {
        return share_Id;
    }

    public void setShare_Id(String share_Id) {
        this.share_Id = share_Id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public boolean isWeb() {
        return web;
    }

    public void setWeb(boolean web) {
        this.web = web;
    }

    public String[] getPosterArray() {
        return posterArray;
    }

    public void setPosterArray(String[] posterArray) {
        this.posterArray = posterArray;
    }

    public ArrayList<KnowledgeSharingBean> getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(ArrayList<KnowledgeSharingBean> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    @Override
	public String execute() throws Exception {
        return SUCCESS;
    }

    public void listKnowledgeSharing() throws Exception {
        if(!(Boolean) ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_admin")) {
            approval = "Y";
        }
        knowledgeList = KnowledgeSharingDAO.getKnowledgeSharing(share_Id, subject,
                "All".equals(approval) ? null:approval, "All".equals(poster) ? null:poster, startTime, endTime, content, pagenum * NumberUtils.toInt(pagesize, -1), NumberUtils.toInt(pagesize, -1), false);
        totalCount = KnowledgeSharingDAO.getKnowledgeSharingCount(share_Id, subject, "All".equals(approval) ? null:approval, "All".equals(poster) ? null:poster, startTime, endTime, content);
    }

    public void listPoster() throws Exception {
        List<KnowledgeSharingBean> posterList = KnowledgeSharingDAO.getPosterList();
        for(KnowledgeSharingBean ksb : posterList) {
            posterArray = ArrayUtils.add(posterArray, ksb.getPoster());
        }
    }

    public String deleteKnowledgeSharing() throws Exception {
        KnowledgeSharingDAO.delete(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), share_Id);
        if(web) {
            return null;
        } else {
            return "success_updateknowledge";
        }
    }
	
	public String updateKnowledgeSharing() throws Exception {
        if("Y".equals(approval)) {
            knowledgeList.add(KnowledgeSharingDAO.approve(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), share_Id));
        } else if ("H".equals(approval)) {
            knowledgeList.add(KnowledgeSharingDAO.hold(String.valueOf(ServletActionContextUtils.getSessionAttribute("mysword_username")), share_Id));
        } else {
            throw new Exception("Unsupport approval flag.");
        }
        if(web) {
            return null;
        } else {
            return "success_updateknowledge";
        }
    }
	
	public String showKnowledgeSharing() throws Exception {
        if((Boolean) ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_admin")) {
            fileStream = KnowledgeSharingDAO.getEmailMessageStream(share_Id);
            return "success_show";
        } else if ((Boolean) ServletActionContextUtils.getSessionAttribute("mysword_knowledge_sharing_admin")){
            KnowledgeSharingBean ksb = KnowledgeSharingDAO.getKnowledgeSharing(share_Id, false);
            filename = ksb.getFilename();
            if("Y".equals(ksb.getApproval())) {
                fileStream = KnowledgeSharingDAO.getEmailMessageStream(share_Id);
                return "success_show";
            } else {
                throw new Exception("Only administrator can download all types of knowledge sharing emails.");
            }
        } else {
            throw new Exception("You are not the knowledge sharing user.");
        }
	}

    public void findKnowledgeSharing() throws Exception {
        knowledgeList.add(KnowledgeSharingDAO.getKnowledgeSharing(share_Id, true));
    }
}
