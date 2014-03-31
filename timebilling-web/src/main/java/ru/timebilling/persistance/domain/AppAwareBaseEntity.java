package ru.timebilling.persistance.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Filter;

import ru.timebilling.persistance.AppNameSupplier;

/**
 * Базовый класс для всех сущностей, специфичных для приложения
 * 
 * @author vshmelev
 * 
 */
@FilterDefs({
	@FilterDef(name = "tb_appNameFilter", parameters={@ParamDef( name="appNameFilterString", type="string" )} )
	})
@Filters({
	@Filter(name="tb_appNameFilter", condition="(appname = :appNameFilterString)")
	})
@MappedSuperclass
public class AppAwareBaseEntity extends BaseEntity {

	@Column(name = "appname", nullable = true, insertable = true, updatable = true, length = 255)
	private String appName;

	@PrePersist
	public void onPersist() {
		if (appName == null) {
			appName = AppNameSupplier.getAppName();
		}
	}
	
	@PostLoad
	@PreUpdate
	@PreRemove
	public void onEntityModification() throws Exception {		
	    if (appName==null || !appName.equals(AppNameSupplier.getAppName())) {
	        throwAccessError();
	    }
	}

	private void throwAccessError() throws Exception{
		// TODO throw specific access error exception
		throw new Exception("access error to entity [" + 
				getId() + "][" + appName + "] for appname [" + AppNameSupplier.getAppName() + "]");
		
	}
}
