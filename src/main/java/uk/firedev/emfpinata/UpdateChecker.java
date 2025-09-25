package uk.firedev.emfpinata;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker {

    private final EMFPinata plugin;

    public UpdateChecker(final EMFPinata plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("UnstableApiUsage")
    public String getVersion() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.modrinth.com/v2/project/nUkvlCGe/version"))
                .header("User-Agent", "EMFPinata/" + plugin.getPluginMeta().getVersion())
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IOException("HTTP " + response.statusCode());
            }

            JSONArray versions = (JSONArray) new JSONParser().parse(response.body());
            if (versions.isEmpty()) {
                return plugin.getPluginMeta().getVersion();
            }

            JSONObject latestVersion = (JSONObject) versions.get(0);
            return latestVersion.get("version_number").toString();
        } catch (Exception e) {
            plugin.getLogger().warning("Update check failed: " + e.getMessage());
            plugin.getLogger().info("Manual update check: https://modrinth.com/plugin/emfpinata/versions");
            return plugin.getPluginMeta().getVersion(); // Fallback
        }

    }

    // Checks for updates, surprisingly
    @Contract(" -> new")
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull CompletableFuture<Boolean> checkUpdate() {
        return CompletableFuture.supplyAsync(() -> {
            ComparableVersion modrinthVersion = new ComparableVersion(new UpdateChecker(plugin).getVersion());
            ComparableVersion serverVersion = new ComparableVersion(plugin.getPluginMeta().getVersion());
            return modrinthVersion.compareTo(serverVersion) > 0;
        });
    }
}
