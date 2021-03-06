package net.sf.anathema.initialization.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.anathema.framework.environment.ResourceLoader;
import net.sf.anathema.framework.environment.dependencies.ExternalResourceFile;
import net.sf.anathema.framework.environment.resources.ResourceFile;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomDataResourceLoader implements ResourceLoader {
  public static final String CUSTOM_FOLDER_NAME = "custom";
  private final File customFolder;

  public CustomDataResourceLoader(RepositoryLocationResolver resolver) {
    File folder = new File(resolver.resolve(), CUSTOM_FOLDER_NAME);
    this.customFolder = new RepositoryFolderWorker().createFolder(folder);
  }

  @Override
  public Set<ResourceFile> getResourcesMatching(final String namePattern) {
    Collection<File> customFiles = Lists.newArrayList(customFolder.listFiles((dir, name) -> name.matches(namePattern)));
    HashSet<ResourceFile> resourceFiles = Sets.newHashSet();
    for (File file : customFiles) {
      resourceFiles.add(new ExternalResourceFile(file));
    }
    return resourceFiles;
  }
}