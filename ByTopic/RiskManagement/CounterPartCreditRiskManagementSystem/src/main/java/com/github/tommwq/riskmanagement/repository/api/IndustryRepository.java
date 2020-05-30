package com.github.tommwq.riskmanagement.repository.api;

import com.github.tommwq.riskmanagement.domain.Industry;
import java.util.List;

public interface IndustryRepository {
  Industry createIndustry(String industryName);
  void updateIndustry(Industry industry);
  List<Industry> queryAllIndustry();
}
