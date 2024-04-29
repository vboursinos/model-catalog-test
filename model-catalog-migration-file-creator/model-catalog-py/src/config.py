from typing import List, Dict
from pathlib import Path
from functools import lru_cache
import os
import json

from pydantic import BaseModel, Field

from models import MlTask, ModelName, Group

# File paths
STATIC_CONFIG = Path(__file__).parent / "static"  # folder for default config files
DEFAULTS = STATIC_CONFIG / "defaults.json"
MODELS_SUPPORT = STATIC_CONFIG / "models_support.json"

# Configuration constants
ENABLE_TIMESERIES = os.getenv("METAML_ENABLE_TIMESERIES", "false") == "true"


# ──────────────────────────────────────────────────────────────────────────── #
# Blacklists
@lru_cache(maxsize=1)  # singleton for a given run
def get_blacklisted_models() -> List[ModelName]:
    """Retrieves the list of blacklisted models, static for a given commit of
    this repository.
    """
    return BLACKLIST.read_text().strip().split('\n')


@lru_cache(maxsize=1)  # singleton for a given run
def get_blacklisted_groups() -> List[str]:
    """Retrieves the list of blacklisted model groups, static for a given commit
    of this repository.
    """
    blacklisted = BLACKLIST_GROUPS.read_text().strip().split('\n')
    # Not included in file, so it can be dynamically changed
    if not ENABLE_TIMESERIES:
        blacklisted.append('Time Series Model')
    return blacklisted


# ──────────────────────────────────────────────────────────────────────────── #
# Groups (subsets of models)
_DEFAULTS_MAP = json.loads(DEFAULTS.read_text())
"""Map of Group (hardcoded subset of models) to ml-task to models"""
# ──────────────────────────────────────────────────────────────────────────── #
# Model Support
_MODELS_SUPPORT = json.loads(MODELS_SUPPORT.read_text())
"""Map of `model name` to `dict` following the `ModelSupport` structure"""


class ModelSupport(BaseModel):
    """Additional fields of capabilities supported by models that we don't want
    to include in metaml's sources directly.

    Loaded from a static JSON in src/metaml_lite/models_info/static1
    """
    decisionTree: bool = Field(False, alias="decision_tree")

    class Config:
        extra = "forbid"


def get_model_support(model_name: ModelName) -> ModelSupport:
    """Loads the model support pydantic model for the given model name"""
    return ModelSupport.parse_obj(_MODELS_SUPPORT.get(model_name, {}))


__all__ = [
    "get_blacklisted_models",
    "get_blacklisted_groups",
    "get_default_groups",
    "ENABLE_TIMESERIES",
]
