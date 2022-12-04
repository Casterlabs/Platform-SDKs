package co.casterlabs.caffeineapi.realtime.viewers;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.caffeineapi.types.CaffeineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Viewer {
    private @NonNull ViewerDetails userDetails;
    private @Nullable CaffeineUser asUser;
    private long joinedAt;

    public @Nullable String getCAID() {
        return this.userDetails.getCAID();
    }

    public boolean isAnonymous() {
        return this.userDetails.getCAID().equals("Anonymous");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Viewer) {
            Viewer other = (Viewer) obj;

            if (!other.isAnonymous()) {
                return other.getCAID().equals(this.getCAID());
            }
        }

        return false;
    }

}
