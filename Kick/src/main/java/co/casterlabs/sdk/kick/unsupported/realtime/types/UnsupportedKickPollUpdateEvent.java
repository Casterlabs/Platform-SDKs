package co.casterlabs.sdk.kick.unsupported.realtime.types;

import java.util.Arrays;
import java.util.Objects;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class UnsupportedKickPollUpdateEvent {
    private String title;
    private PollOption[] options;

    @JsonField("duration")
    private int durationSeconds;

    @JsonField("remaining")
    private int remainingSeconds;

    @JsonField("result_display_duration")
    private int resultDisplayDurationSeconds;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class PollOption {
        private int id;
        private String label;
        private int votes;

        @Override
        public int hashCode() {
            return Objects.hash(this.id, this.label);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.durationSeconds, this.resultDisplayDurationSeconds, Arrays.hashCode(this.options));
    }

}
